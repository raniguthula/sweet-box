package in_Apcfss.Apofms.api.ofmsapi.services;

import java.util.List;
import java.util.Map;

public interface ReportsService {

	//-------------BankBook Report-------------
	
	List<Map<String, String>> getBankBookReport(String fromdate, String todate, String user_id);
	
	List<Map<String, String>> getBankBookTotalCredit(String fromdate, String todate, String user_id);

	List<Map<String, String>> getBankBookTotalDebit(String fromdate, String todate, String user_id);
	
	//--------------Search Employee Report(Cadre Wise)------------
	
	List<Map<String, String>> getDistrictName(String code);
	
	List<Map<String, String>> getSearchEmployeeReport(String empname, int prdist, int designation);
	
	List<Map<String, String>> getSearchEmployeeReportViewData(String empid);
	
	//---------------BankSubsidary Report------------
	
	List<Map<String, String>> getBankSubsidiaryNames(String security_id);

	List<Map<String, String>> getBankSubsidiaryReport(String fromdate, String todate, String security_id,
			String banknameaccountno);

	List<Map<String, String>> getTotalBankSubsidiaryReport(String fromdate, String todate, String security_id,
			String banknameaccountno);

	List<Map<String, String>> getOpeningBalanceBankSubsidiaryReport(String fromdate,String banknameaccountno);

	
	//---------------CashBankReceipt Report------------
	
	List<Map<String, String>> getCashBankDropdown();
	
	List<Map<String, String>> getCashBankReceiptsReport(String fromdate, String todate, 
			String cash_bank, String security_id);

	List<Map<String, String>> getTotalOfCashBankReceiptsReport(String fromdate, String todate, String cash_bank,
			String security_id);
	
	List<Map<String, String>> getReceiptDataByTransactionID(String payment_receipt_id);
	
	List<Map<String, String>> getReceiptDataSubheadsByTransactionID(String payment_receipt_id);
	
	List<Map<String, String>> getReceiptDataByReceiptNo(String payment_receipt_id, String receiptno);
	
	List<Map<String, String>> getReceiptDataSubheadsByReceiptNo(String payment_receipt_id, String receiptno);
	
	List<Map<String, String>> getReceiptDataByHeadID(String payment_receipt_id, String headid);

	//---------------CashBankPayment Report------------
	
	List<Map<String, String>> getCashBankPaymentReport(String fromdate, String todate, String cash_bank,
			String security_id);

	List<Map<String, String>> getTotalOfCashBankPaymentReport(String fromdate, String todate, String cash_bank,
			String security_id);
	
	List<Map<String, String>> getPaymentDataByTransactionID(String payment_receipt_id);

	List<Map<String, String>> getPaymentDataSubheadsByTransactionID(String payment_receipt_id);

	List<Map<String, String>> getPaymentDataByReceiptNo(String payment_receipt_id, String receiptno);

	List<Map<String, String>> getPaymentDataSubheadsByReceiptNo(String payment_receipt_id, String receiptno);

	List<Map<String, String>> getPaymentDataByHeadID(String payment_receipt_id, String headid);
	
	List<Map<String, String>> getSubheadsBySeqID(String headid, String subheadseqid);

	//-----------------Journal Voucher Report-----------------
	
	List<Map<String, String>> getJournalVoucherReport(String fromdate, String todate, String security_id);


	List<Map<String, String>> getTotalJournalVoucherReport(String fromdate, String todate, String security_id);

	List<Map<String, String>> getJournalVoucherReportByTransanctionID(String payment_receipt_id);
	
	List<Map<String, String>> getJournalVoucherReportSubheadsByTransanctionID(String payment_receipt_id);
	
	//-----------------Journal Register Report ------------------

	List<Map<String, String>> getJournalRegsiterReport(String fromdate, String todate, String security_id);

	
	// -------------------SubLedger Report ---------------------------
	
	List<Map<String, String>> getSubLedgerReport(String fromdate, String todate, String security_id, String headid,
			String subheadseqid);

	List<Map<String, String>> getSubLedgerReportByTransanctionID(String payment_receipt_id);

	List<Map<String, String>> getSubLedgerReportSubHeadsByTransanctionID(String payment_receipt_id);

	//------------------GeneralLedger Report------------------
	
	List<Map<String, String>> getGeneralLedgerReport(String fromdate, String todate, String security_id, String headid);

	List<Map<String, String>> getOpeningBalGeneralLedgerReport(String todate, String security_id,
			String headid);

	// ----------------HeadWise Report ------------------------

	List<Map<String, String>> getHeadWiseReportDistrict(String code);

	List<Map<String, String>> getHeadwiseReportReceipts(String fromdate, String todate, String headid);

	List<Map<String, String>> getHeadwiseReportPayments(String fromdate, String todate, String headid);

	List<Map<String, String>> getHeadwiseReportJournalVouchers(String fromdate, String todate, String headid);



	
}
