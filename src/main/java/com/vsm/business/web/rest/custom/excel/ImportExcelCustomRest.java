package com.vsm.business.web.rest.custom.excel;

import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.domain.AttachmentFile;
import com.vsm.business.utils.ExcelUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * REST controller for managing {@link AttachmentFile}.
 */
@RestController
@RequestMapping("/api")
public class ImportExcelCustomRest {

    private final Logger log = LoggerFactory.getLogger(ImportExcelCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceAttachmentFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExcelUtils excelUtils;

    public ImportExcelCustomRest(ExcelUtils excelUtils) {
        this.excelUtils = excelUtils;
    }

    @PostMapping("/import-excel")
    public ResponseEntity<IResponseMessage> importExcel(@RequestParam("file")MultipartFile file) throws IOException, InvalidFormatException {
        File fileTemp = new File(new File(".").getAbsolutePath() + "/temp/" + file.getOriginalFilename());
        file.transferTo(fileTemp);
        JSONObject jsonObject = this.excelUtils.readExcel(fileTemp);
        fileTemp.delete();
        return ResponseEntity.ok().body(new LoadedMessage(jsonObject.toString()));
    }
}
