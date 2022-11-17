package in_Apcfss.Apofms.api.ofmsapi.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in_Apcfss.Apofms.api.ofmsapi.services.HeadWiseReportService;
import in_Apcfss.Apofms.api.ofmsapi.services.ReportsService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ReportsController {
	
	@Autowired
	HeadWiseReportService headWiseReportService;
	
	@Autowired
	ReportsService reportsService;
	
	@GetMapping("/HeadWiseDropDown") // It can be used in mstheads and journal voucher
	public List<Map<String,String>> getreportHeads(){
		return headWiseReportService.getreportHeads();
	}
	
	@GetMapping("/HeadWiseReport")
	public List<Map<String, String>> getreport(@RequestParam (name="fromdate")  String fromdate,@RequestParam 
			(name="todate") String todate,@RequestParam (required=false) String headid,@RequestParam  String secId){
		return headWiseReportService.getTabledata(fromdate,todate,headid,secId);
	}
	
	//-------------BankBook Report-------------
	//(We can also use security_id='01' in condition instead of user_id---data remains same)
	
	@GetMapping("getBankBookReport")
	public List<Map<String, String>> getBankBookReport(@RequestParam String fromdate, 
			@RequestParam String todate, @RequestParam String user_id){
		return reportsService.getBankBookReport(fromdate, todate, user_id);
	}
	
	@GetMapping("getBankBookTotalCredit")  // Payment Amount
	public List<Map<String, String>> getBankBookTotalCredit(@RequestParam String fromdate, 
			@RequestParam String todate, @RequestParam String user_id){
		return reportsService.getBankBookTotalCredit(fromdate, todate, user_id);
	}
	
	@GetMapping("getBankBookTotalDebit")   // Receipt Amount
	public List<Map<String, String>> getBankBookTotalDebit(@RequestParam String fromdate, 
			@RequestParam String todate, @RequestParam String user_id){
		return reportsService.getBankBookTotalDebit(fromdate, todate, user_id);
	}
	
	//--------------Search Employee Report(Cadre Wise)------------
	
	@GetMapping("getDistrictName")
	public List<Map<String, String>> getDistrictName(@RequestParam String code){
		return reportsService.getDistrictName(code);
	}
	
	// We can use designation Dropdown from CadreWiseController
	/*   http://172.16.150.146:8082/ofmsapi/getCadreWiseDesignationDropdown  */	
	
	@GetMapping("SearchEmployeeReport")
	public List<Map<String, String>> getSearchEmployeeReport(@RequestParam String empname, @RequestParam int prdist,
			@RequestParam int designation){
		return reportsService.getSearchEmployeeReport(empname, prdist, designation);
	}
	
	@GetMapping("SearchEmployeeReportViewData")
	public List<Map<String, String>> getSearchEmployeeReportViewData(@RequestParam String empid){
		return reportsService.getSearchEmployeeReportViewData(empid);
	}
	
	/* ------- Use below apis for getreport from CadreWiseController ---- */
	
	/*  http://172.16.150.146:8082/ofmsapi/getCadreWiseDetailsByEmpID_SubsequentPromotions?empid=1030 */
	
	/* http://172.16.150.146:8082/ofmsapi/getCadreWiseDetailsByEmpID_POWI?empid=1030 */
	
	/*    ---------------BankSubsidary Report------------   */
	
	@GetMapping("getBankSubsidiaryNames")
	public List<Map<String, String>> getBankSubsidiartyNames(@RequestParam String security_id) {
		return reportsService.getBankSubsidiaryNames(security_id);
	}
	
	@GetMapping("getBankSubsidiaryReport")
	public List<Map<String, String>> getBankSubsidiaryReport(@RequestParam String fromdate, 
			@RequestParam String todate, @RequestParam String security_id,
			@RequestParam String banknameaccountno) {
		return reportsService.getBankSubsidiaryReport(fromdate, todate, security_id, banknameaccountno);
	}
	
	@GetMapping("getOpeningBalanceBankSubsidiaryReport")
	public List<Map<String, String>> getOpeningBalanceBankSubsidiaryReport(@RequestParam String fromdate, @RequestParam String banknameaccountno) {
		return reportsService.getOpeningBalanceBankSubsidiaryReport(fromdate, banknameaccountno);
	}
	
	@GetMapping("getTotalBankSubsidiaryReport")
	public List<Map<String, String>> getTotalBankSubsidiaryReport(@RequestParam String fromdate, 
			@RequestParam String todate, @RequestParam String security_id,
			@RequestParam String banknameaccountno) {
		return reportsService.getTotalBankSubsidiaryReport(fromdate, todate, security_id, 
				banknameaccountno);
	}
	
	//---------------CashBankReceipt Report------------
	
	@GetMapping("getCashBankDropdown")
	public List<Map<String, String>> getCashBankDropdown(){
		return reportsService.getCashBankDropdown();
	}
	
	@GetMapping("getCashBankReceiptsReport")
	public List<Map<String, String>> getCashBankReceiptsReport(@RequestParam String fromdate, 
			@RequestParam String todate, @RequestParam String cash_bank, @RequestParam String security_id){
		return reportsService.getCashBankReceiptsReport(fromdate, todate, cash_bank, security_id);
	}
	
	@GetMapping("getTotalOfCashBankReceiptsReport")
	public List<Map<String, String>> getTotalOfCashBankReceiptsReport(@RequestParam String fromdate, 
			@RequestParam String todate, @RequestParam String cash_bank, @RequestParam String security_id){
		return reportsService.getTotalOfCashBankReceiptsReport(fromdate, todate, cash_bank, security_id);
	}
	
	@GetMapping("getReceiptDataByTransactionID")
	public List<Map<String, String>> getReceiptDataByTransactionID(@RequestParam String payment_receipt_id){
		return reportsService.getReceiptDataByTransactionID(payment_receipt_id);
	}
	
	@GetMapping("getReceiptDataSubheadsByTransactionID")
	public List<Map<String, String>> getReceiptDataSubheadsByTransactionID(@RequestParam String payment_receipt_id){
		return reportsService.getReceiptDataSubheadsByTransactionID(payment_receipt_id);
	}
	
	@GetMapping("getReceiptDataByReceiptNo")
	public List<Map<String, String>> getReceiptDataByReceiptNo(@RequestParam String payment_receipt_id, 
			@RequestParam String receiptno){
		return reportsService.getReceiptDataByReceiptNo(payment_receipt_id, receiptno);
	}
	
	@GetMapping("getReceiptDataSubheadsByReceiptNo")
	public List<Map<String, String>> getReceiptDataSubheadsByReceiptNo(@RequestParam String payment_receipt_id, 
			@RequestParam String receiptno){
		return reportsService.getReceiptDataSubheadsByReceiptNo(payment_receipt_id, receiptno);
	}
	
	@GetMapping("getReceiptDataByHeadID")
	public List<Map<String, String>> getReceiptDataByHeadID(@RequestParam String payment_receipt_id, 
			@RequestParam String headid){
		return reportsService.getReceiptDataByHeadID(payment_receipt_id, headid);
	}
	
	//---------------CashBankPayment Report------------
	
	@GetMapping("getCashBankPaymentReport")
	public List<Map<String, String>> getCashBankPaymentReport(@RequestParam String fromdate, 
			@RequestParam String todate, @RequestParam String cash_bank, @RequestParam String security_id){
		return reportsService.getCashBankPaymentReport(fromdate, todate, cash_bank, security_id);
	}
	
	@GetMapping("getTotalOfCashBankPaymentReport")
	public List<Map<String, String>> getTotalOfCashBankPaymentReport(@RequestParam String fromdate, 
			@RequestParam String todate, @RequestParam String cash_bank, @RequestParam String security_id){
		return reportsService.getTotalOfCashBankPaymentReport(fromdate, todate, cash_bank, security_id);
	}
	
	//http://172.16.150.146:8082/ofmsapi/getPaymentDataByTransactionID?payment_receipt_id=P010408221
	@GetMapping("getPaymentDataByTransactionID")
	public List<Map<String, String>> getPaymentDataByTransactionID(@RequestParam String payment_receipt_id){
		return reportsService.getPaymentDataByTransactionID(payment_receipt_id);
	}
	
	//http://172.16.150.146:8082/ofmsapi/getPaymentDataSubheadsByTransactionID?payment_receipt_id=P010408221
	@GetMapping("getPaymentDataSubheadsByTransactionID")
	public List<Map<String, String>> getPaymentDataSubheadsByTransactionID(@RequestParam String payment_receipt_id){
		return reportsService.getPaymentDataSubheadsByTransactionID(payment_receipt_id);
	}
	
	//http://172.16.150.146:8082/ofmsapi/getPaymentDataByReceiptNo?receiptno=BP142&payment_receipt_id=P010408221
	@GetMapping("getPaymentDataByReceiptNo")
	public List<Map<String, String>> getPaymentDataByReceiptNo(@RequestParam String payment_receipt_id, 
			@RequestParam String receiptno){
		return reportsService.getPaymentDataByReceiptNo(payment_receipt_id, receiptno);
	}
	
	//http://172.16.150.146:8082/ofmsapi/getPaymentDataSubheadsByReceiptNo?receiptno=BP142&payment_receipt_id=P010408221
	@GetMapping("getPaymentDataSubheadsByReceiptNo")
	public List<Map<String, String>> getPaymentDataSubheadsByReceiptNo(@RequestParam String payment_receipt_id, 
			@RequestParam String receiptno){
		return reportsService.getPaymentDataSubheadsByReceiptNo(payment_receipt_id, receiptno);
	}
	
	//http://172.16.150.146:8082/ofmsapi/getPaymentDataByHeadID?headid=20668&payment_receipt_id=P010408221
	@GetMapping("getPaymentDataByHeadID")
	public List<Map<String, String>> getPaymentDataByHeadID(@RequestParam String payment_receipt_id, 
			@RequestParam String headid){
		return reportsService.getPaymentDataByHeadID(payment_receipt_id, headid);
	}
	
	@GetMapping("getSubheadsBySeqID")
	public List<Map<String, String>> getSubheadsBySeqID(@RequestParam String headid, 
			@RequestParam String subheadseqid){
		return reportsService.getSubheadsBySeqID(headid, subheadseqid);
	}
	
	//-----------------Journal Voucher Report-----------------
	
	@GetMapping("getJournalVoucherReport")
	public List<Map<String, String>> getJournalVoucherReport(@RequestParam String fromdate, 
			@RequestParam String todate, @RequestParam String security_id){
		return reportsService.getJournalVoucherReport(fromdate, todate, security_id);
	}
	
	@GetMapping("getTotalJournalVoucherReport")
	public List<Map<String, String>> getTotalJournalVoucherReport(@RequestParam String fromdate, 
			@RequestParam String todate, @RequestParam String security_id){
		return reportsService.getTotalJournalVoucherReport(fromdate, todate, security_id);
	}
	
	@GetMapping("getJournalVoucherReportByTransanctionID")
	public List<Map<String, String>> getJournalVoucherReportByTransanctionID(@RequestParam String payment_receipt_id){
		return reportsService.getJournalVoucherReportByTransanctionID(payment_receipt_id);
	}
	
	@GetMapping("getJournalVoucherReportSubheadsByTransanctionID")
	public List<Map<String, String>> getJournalVoucherReportSubheadsByTransanctionID(@RequestParam String payment_receipt_id){
		return reportsService.getJournalVoucherReportSubheadsByTransanctionID(payment_receipt_id);
	}
	
	// Use update api from journalvouchercontroller
	//http://172.16.150.146:8082/ofmsapi/updateJournalVoucherReport/J013008222
	/*
	 * { "voucher_id":"17", "date":"2022-08-30",
	 * "description":"Being the provision is made towards SALARIES PAYABLE for this month(Regular)"
	 * , "debit":"5496104", "credit":"5496104" }
	 */
	
	//-----------------Journal Register Report ------------------
	
	@GetMapping("/JournalRegisterReportDetails")
	public List<Map<String,String>> getJournalRegsiterReport(@RequestParam String fromdate,
			@RequestParam String todate,@RequestParam String security_id){
		return reportsService.getJournalRegsiterReport(fromdate, todate,security_id);
	}
	
	// -------------------SubLedger Report ---------------------------
	
	/* Use Head and SubHead Dropdown API's from JournalVoucherController getHeadsNameDropdown, getSubheadsNameDropdown*/
	
	//http://172.16.150.146:8082/ofmsapi/getHeadsNameDropdown
	//http://172.16.150.146:8082/ofmsapi/getSubheadsNameDropdown/10101
	
	//http://172.16.150.146:8082/ofmsapi/SubLedgerReport?security_id=01&fromdate=2022-08-01&todate=2022-10-15&headid=All&subheadseqid=All
	// http://172.16.150.146:8082/ofmsapi/SubLedgerReport?security_id=01&fromdate=2022-08-01&todate=2022-10-15&headid=10101&subheadseqid=15173
	
	@GetMapping("SubLedgerReport")
	public List<Map<String, String>> getSubLedgerReport(@RequestParam String fromdate, @RequestParam String todate,
			@RequestParam String security_id, @RequestParam String headid, @RequestParam String subheadseqid){
		return reportsService.getSubLedgerReport(fromdate, todate, security_id, headid, subheadseqid);
	}
	
	//http://172.16.150.146:8082/ofmsapi/getSubLedgerReportByTransanctionID?payment_receipt_id=P010408221
	@GetMapping("getSubLedgerReportByTransanctionID")
	public List<Map<String, String>> getSubLedgerReportByTransanctionID(@RequestParam String payment_receipt_id){
		return reportsService.getSubLedgerReportByTransanctionID(payment_receipt_id);
	}
	
	//http://172.16.150.146:8082/ofmsapi/getSubLedgerReportSubHeadsByTransanctionID?payment_receipt_id=P010408221
	@GetMapping("getSubLedgerReportSubHeadsByTransanctionID")
	public List<Map<String, String>> getSubLedgerReportSubHeadsByTransanctionID(@RequestParam String payment_receipt_id){
		return reportsService.getSubLedgerReportSubHeadsByTransanctionID(payment_receipt_id);
	}
	
	//------------------GeneralLedger Report------------------
	//http://172.16.150.146:8082/ofmsapi/GeneralLedgerReport?headid=20101&security_id=01&fromdate=2022-06-01&todate=2022-10-17
	@GetMapping("GeneralLedgerReport")
	public List<Map<String, String>> getGeneralLedgerReport(@RequestParam String fromdate, @RequestParam String todate,
			@RequestParam String security_id, @RequestParam String headid){
		return reportsService.getGeneralLedgerReport(fromdate, todate, security_id, headid);
	}
	
	//http://172.16.150.146:8082/ofmsapi/getOpeningBalGeneralLedgerReport?headid=20101&security_id=01&todate=2022-10-17
	@GetMapping("getOpeningBalGeneralLedgerReport")
	public List<Map<String, String>> getOpeningBalGeneralLedgerReport(@RequestParam String todate,
			@RequestParam String security_id, @RequestParam String headid){
		return reportsService.getOpeningBalGeneralLedgerReport(todate, security_id, headid);
	}
	
	// ----------------HeadWise Report ------------------------
	
	//http://172.16.150.146:8082/ofmsapi/getHeadWiseReportDistrict?code=01
	@GetMapping("getHeadWiseReportDistrict")
	public List<Map<String, String>> getHeadWiseReportDistrict(@RequestParam String code){
		return reportsService.getHeadWiseReportDistrict(code);
	}
	
	//http://172.16.150.146:8082/ofmsapi/getHeadwiseReportReceipts?headid=All&fromdate=2022-06-01&todate=2022-10-17
	//http://172.16.150.146:8082/ofmsapi/getHeadwiseReportReceipts?headid=40801&fromdate=2022-06-01&todate=2022-10-17
	@GetMapping("getHeadwiseReportReceipts")
	public List<Map<String, String>> getHeadwiseReportReceipts(@RequestParam String fromdate, @RequestParam String todate,
			@RequestParam String headid){
		return reportsService.getHeadwiseReportReceipts(fromdate, todate, headid);
	}
	
	//http://172.16.150.146:8082/ofmsapi/getHeadwiseReportPayments?fromdate=2022-06-01&todate=2022-10-17&headid=All
	//http://172.16.150.146:8082/ofmsapi/getHeadwiseReportPayments?headid=40801&fromdate=2022-06-01&todate=2022-10-17
	@GetMapping("getHeadwiseReportPayments")
	public List<Map<String, String>> getHeadwiseReportPayments(@RequestParam String fromdate, @RequestParam String todate,
			@RequestParam String headid){
		return reportsService.getHeadwiseReportPayments(fromdate, todate, headid);
	}
	
	//http://172.16.150.146:8082/ofmsapi/getHeadwiseReportJournalVouchers?fromdate=2022-06-01&todate=2022-10-17&headid=All
	//http://172.16.150.146:8082/ofmsapi/getHeadwiseReportJournalVouchers?headid=40801&fromdate=2022-06-01&todate=2022-10-17
	@GetMapping("getHeadwiseReportJournalVouchers")
	public List<Map<String, String>> getHeadwiseReportJournalVouchers(@RequestParam String fromdate, @RequestParam String todate,
			@RequestParam String headid){
		return reportsService.getHeadwiseReportJournalVouchers(fromdate, todate, headid);
	}
}
