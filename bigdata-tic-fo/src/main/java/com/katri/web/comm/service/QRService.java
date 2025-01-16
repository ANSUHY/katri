package com.katri.web.comm.service;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.katri.common.FileConst;
import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.web.comm.model.FileDto;
import com.katri.web.comm.model.QRReq;
import com.nhncorp.lucy.security.xss.XssPreventer;

import lombok.RequiredArgsConstructor;

/***************************************************
 * <ul>
 * <li>업무 그룹명 : QR 서비스</li>
 * <li>서브 업무명 : QR 서비스</li>
 * <li>설		 명 : QR생성, QR업로드 QR 다운로드 등 QR 관련 기능 제공</li>
 * <li>작   성   자 : ASH</li>
 * <li>작   성   일 : 2022. 09. 07.</li>
 * </ul>
 * <pre>
 * ======================================
 * 변경자/변경일 :
 * 변경사유/내역 :
 * --------------------------------------
 * 변경자/변경일 :
 * 변경사유/내역 :
 * ======================================
 * </pre>
 ***************************************************/
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class QRService {
	private final String localLogoPath = "C:/KATRi/nodbfile/qr_logo.png"; // 박태우 신규 작성(2023-01-04)
	private final String prdLogoPath = "/nas/nodbfile/qr_logo.png"; // 박태우 신규 작성(2023-01-04)   /nas/nodbfile/qr_logo.png
	
	/** 파일 Service */
	private final FileService fileService;

	/*****************************************************
	 * QR 파일 정보 조회 (없으면 저장 후 파일정보)
	 * @param qrReq QR 등록정보
	 * @return FileDto QR 파일정보
	 * @throws Exception
	*****************************************************/
	public FileDto getQRFileData(QRReq qrReq) throws Exception {

		FileDto fileDto = new FileDto();

		// [[0]] URL convertDBToHtml 해주기
		qrReq.setQrUrl(XssPreventer.unescape(qrReq.getQrUrl()));

		// [[1]] 기존에 파일 있는지 검색()
		/* 1-1. 조회하기위한 FILEDTO셋팅 */
		FileDto srchFileDto =  new FileDto();
		srchFileDto.setFileDivNm(FileConst.File.FILE_DIV_NM_QR_FILE); //파일구분명
		srchFileDto.setRefDivVal(qrReq.getQrRefDivVal()); //참조구분값
		/* 1-2. 검색 */
		fileDto = fileService.selectFile(srchFileDto);

		// [[2]] 만약에 없으면 생성 및 저장
		if(fileDto == null) {
			fileDto =  this.makeQRFile(qrReq);
		}

		return fileDto;

	}

	/*****************************************************
	 * QR파일 생성 + 저장(물리 + 논리)
	 * @param qrReq QR 등록정보
	 * @return FileDto QR 등록된 파일 정보
	 * @throws Exception
	*****************************************************/
	public FileDto makeQRFile(QRReq qrReq) throws Exception {

		FileDto fileDto = new FileDto();

		/* [[1]]. QR 생성하기 위한 값 셋팅 + bufferedImage 생성 */
		QRCodeWriter qrCodeWriter = new QRCodeWriter();

		// 1-1. url 한글 코드 깨뜨리기
		String utfUrl = new String(qrReq.getQrUrl().getBytes("UTF-8"), "ISO-8859-1");
		qrReq.setQrEncodeUrl(utfUrl);

		// 1-2. BitMatrix 객체 생성 (url 지정 + width/height값 지정)
		BitMatrix bitMatrix = qrCodeWriter.encode(qrReq.getQrEncodeUrl(), BarcodeFormat.QR_CODE, 98, 98); // 98 사이즈로 박태우 수정(2023-01-04)

		// 1-3. 색 지정
		int qrcodeColor	=	0xFF000000;		// 큐알코드 바코드색상값(마젠타 0xFFFF00FF, 파란색 계열 0xFF2e4e96, 노란색 계열 0xFF766c15) // white 색상으로 박태우 수정(2023-01-04)
		int backgroundColor = 0xFFFFFFFF;	// 큐알코드 배경색상값
		MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrcodeColor,backgroundColor);

		// 1-4. bufferedImage 생성
		BufferedImage bufferedImage	= MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);

		/* [[2]]. QR파일 저장 (물리 + 논리) */
		fileDto = this.saveQRFile(bufferedImage, qrReq);

		return fileDto;

	}

	/*****************************************************
	 * QR파일 저장 (물리 + 논리)
	 * @param bufferedImage IMG 객체
	 * @param qrReq QR 등록정보
	 * @return FileDto QR 등록된 파일 정보
	 * @throws Exception
	*****************************************************/
	/* 시작. 박태우 신규 작성(2023-01-04) */
	public BufferedImage medianSmooth(BufferedImage img) {
        final int MSIZE = 3;

        final int w = img.getWidth();
        final int h = img.getHeight();
        Color[] surroundedPixel = new Color[9];
        int[] R = new int[9];
        int[] G = new int[9];
        int[] B = new int[9];
        final BufferedImage finalImage = new BufferedImage(w, h, img.getType());

        System.out.println("안녕 111");
        for (int j = 1; j < img.getHeight() - 1; j++) {
            for (int i = 1; i < img.getWidth() - 1; i++) {
                surroundedPixel[0] = new Color(img.getRGB(i - 1, j - 1));
                surroundedPixel[1] = new Color(img.getRGB(i - 1, j));
                surroundedPixel[2] = new Color(img.getRGB(i - 1, j + 1));
                surroundedPixel[3] = new Color(img.getRGB(i, j + 1));
                surroundedPixel[4] = new Color(img.getRGB(i + 1, j + 1));
                surroundedPixel[5] = new Color(img.getRGB(i + 1, j));
                surroundedPixel[6] = new Color(img.getRGB(i + 1, j - 1));
                surroundedPixel[7] = new Color(img.getRGB(i, j - 1));
                surroundedPixel[8] = new Color(img.getRGB(i, j));
                for (int k = 0; k < 9; k++) {
                    R[k] = surroundedPixel[k].getRed();
                    B[k] = surroundedPixel[k].getBlue();
                    G[k] = surroundedPixel[k].getGreen();
                }
                Arrays.sort(R);
                Arrays.sort(G);
                Arrays.sort(B);
                finalImage.setRGB(i, j, new Color(R[4], B[4], G[4]).getRGB());
            }
        }

        return finalImage;
    }
    /* 끝. 박태우 신규 작성(2023-01-04) */
	
	public FileDto saveQRFile(BufferedImage bufferedImage, QRReq qrReq) throws Exception {
		/* 시작. 박태우 신규 작성(2023-01-04) */
		// Load logo image
        BufferedImage overly = ImageIO.read(new File(prdLogoPath));   //  localLogoPath   or  prdLogoPath !!!!
        
        // Conv. Blur(Kernel) 적용
        //overly = medianSmooth(overly);
        
        /*
        float[] gaussianKernel3x3 = {
        	    1/16f, 1/8f, 1/16f, 
        	    1/8f, 1/4f, 1/8f, 
        	    1/16f, 1/8f, 1/16f, 
        	};
        float[] gaussianKernel5x5 = {
        	    1/273f, 4/273f, 7/273f, 4/273f, 1/273f,
        	    4/273f, 16/273f, 26/273f, 16/273f, 4/273f,
        	    7/273f, 26/273f, 41/273f, 26/273f, 7/273f,
        	    4/273f, 16/273f, 26/273f, 16/273f, 4/273f,
        	    1/273f, 4/273f, 7/273f, 4/273f, 1/273f
        	};
        float[] lowPassKernel3x3 = {
        		1/9f, 1/9f, 1/9f,
        		1/9f, 1/9f, 1/9f,
        		1/9f, 1/9f, 1/9
        	};
        float[] highPassKernel3x3 = {
        		-1/9f, -1/9f, -1/9f,
        		-1/9f,  8/9f, -1/9f,
        		-1/9f, -1/9f, -1/9f
        	};
        Kernel kernel = new Kernel(3, 3, gaussianKernel3x3);
        BufferedImageOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        overly = op.filter(overly, null);
        */

        // Calculate the delta height and width between QR code and logo
        int deltaHeight = bufferedImage.getHeight() - overly.getHeight();
        int deltaWidth = bufferedImage.getWidth() - overly.getWidth();

        // Initialize combined image
        int qrMaxHeight = bufferedImage.getHeight() + 11;
        int qrMaxWidth = bufferedImage.getWidth()   + 11;
        BufferedImage combined = new BufferedImage(qrMaxHeight, qrMaxWidth, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) combined.getGraphics();

        // Write QR code to new image at position 5/10
        g2d.drawImage(bufferedImage, 5, 10, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        // Write logo into combine image at position (deltaWidth / 2) and
        // (deltaHeight / 2). Background: Left/Right and Top/Bottom must be
        // the same space for the logo to be centered
        g2d.drawImage(overly, (int) 3, (int) 3, null); // (int)Math.round(deltaWidth / 2), (int)Math.round(deltaHeight / 2)
        /* 끝. 박태우 신규 작성(2023-01-04) */
		
		

		FileDto fileDto = new FileDto();

		// 물리저장 + 파일dto
		fileDto = fileService.savePhyQRImageFileReturnFile(FileConst.File.FILE_DIV_NM_QR_FILE, combined, "png", qrReq);  // bufferedImage -> combined 박태우 수정(2023-01-04)
		if(fileDto == null) {
			throw new CustomMessageException("QR물리 저장중 오류");
		}

		// LIST에 FileDto넣기
		List<FileDto> listFileDto = new ArrayList<FileDto>(); // FileDto를 넣을 리스트
		listFileDto.add(fileDto);

		// 파일테이블 저장[tb_file_mng]
		listFileDto = fileService.saveDBFile(qrReq.getQrRefDivVal(), listFileDto);
		fileDto = listFileDto.get(0); //file_sn가 들어있음

		return fileDto;

	}


}
