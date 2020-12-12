package com.academy.controller;


import com.academy.model.Academy0401Request;
import com.academy.model.ReceiptMain;
import com.academy.service.*;
import com.academy.vo.StdntPaymentRecord;
import com.academy.vo.StdntPaymentRecordMain;
import com.academy.vo.StdntSignUpRecord;
import com.academy.vo.StudentInfo;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/academy04")
public class Academy04Controller {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private @Value("${location.reciptFile}")
    String reciptLocation;

    @Autowired
    StudentService studentService;
    @Autowired
    CourseFeeService courseFeeService;
    @Autowired
    PaymentRecordService paymentRecordService;
    @Autowired
    SignUpRecordService signUpRecordService;
    @Autowired
    ReceiptService receiptService;

    @PostMapping("/01")
    public String printReceipt(@RequestBody Academy0401Request academy0401Request){
        try{

            logger.debug("printReceipt_academy0401Request:{}", academy0401Request.getStudentIdList());

            Resource resource = new ClassPathResource("jaspertemplate/receipt.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(resource.getFile().getPath());

            JRDataSource dataSource = null;

            List<ReceiptMain> receiptMainList = new ArrayList<>();

            int paymentMonth;

            //判斷20號之後，用當月去執行後續的查詢。
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            int year = cal.get(Calendar.YEAR)-1911;
            int month = cal.get(Calendar.MONTH) +1;
            int day = cal.get(Calendar.DAY_OF_MONTH);

            if(day > 20){
                if(month == 12){
                    paymentMonth = 1 ;
                }else{
                    paymentMonth = month + 1 ;
                }
            }else{
                paymentMonth = month;
            }

            //用, 將傳入的學生ID切開。
            String[] stdntIds = academy0401Request.getStudentIdList().split(",");

            for(String stdntId : stdntIds){

                //第一查詢：學生資料主檔(GET 學生姓名)。
                StudentInfo studentInfo = studentService.findbyStdntId(Integer.valueOf(stdntId));

                //第二查詢：報名的課程有哪些(GET 課程與費用)。
                List<StdntSignUpRecord> stdntSignUpRecordList = signUpRecordService.queryStdntSignUpRecord(Integer.valueOf(stdntId));

                //第三查詢：繳費紀錄檔取得繳費細項(可以用主檔：用「學生ID」、「學生年級」、「繳費月份」去串)。
                StdntPaymentRecordMain stdntPaymentRecordMain = paymentRecordService.
                        queryPaymentRecordMainByStdntIdAndGradeAndPaymentMonth(Integer.valueOf(stdntId), studentInfo.getGrade(), paymentMonth);

                List<StdntPaymentRecord> stdntPaymentRecordList = paymentRecordService.queryPaymentRecordByMainId(stdntPaymentRecordMain.getId());

                ReceiptMain receiptMain;

                receiptMain = receiptService.fetchReciptMainFromData(studentInfo, stdntSignUpRecordList, year, paymentMonth, stdntPaymentRecordList);

                receiptMainList.add(receiptMain);
            }

            dataSource = new JRBeanCollectionDataSource(receiptMainList);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);

            String fileName = "recipt_month_" + paymentMonth + ".pdf";

            JasperExportManager.exportReportToPdfFile(jasperPrint, reciptLocation + fileName);
            return "success";

        }catch(Exception e){
            logger.debug("printReceipt Error:", e);
            return "M9999";
        }
    }
}
