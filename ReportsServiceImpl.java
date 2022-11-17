package in_Apcfss.Apofms.api.ofmsapi.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in_Apcfss.Apofms.api.ofmsapi.Repositories.ReportsRepo;
import in_Apcfss.Apofms.api.ofmsapi.services.ReportsService;

@Service
public class ReportsServiceImpl implements ReportsService {

	@Autowired
	ReportsRepo reportsRepo;

	// -------------BankBook Report-------------

	public List<Map<String, String>> getBankBookReport(String fromdate, String todate, String user_id) {
		return reportsRepo.getBankBookReport(fromdate, todate, user_id);
	}
	
	public List<Map<String, String>> getBankBookTotalCredit(String fromdate, String todate, String user_id){
		return reportsRepo.getBankBookTotalCredit(fromdate, todate, user_id);
	}

	public List<Map<String, String>> getBankBookTotalDebit(String fromdate, String todate, String user_id){
		return reportsRepo.getBankBookTotalDebit(fromdate, todate, user_id);
	}

	//--------------Search Employee Report(Cadre Wise)------------
	
	public List<Map<String, String>> getDistrictName(String code){
		return reportsRepo.getDistrictName(code);
	}
	
	public List<Map<String, String>> getSearchEmployeeReport(String empname, int prdist, int designation){
		return reportsRepo.getSearchEmployeeReport(empname, prdist, designation);
	}
	
	public List<Map<String, String>> getSearchEmployeeReportViewData(String empid){
		return reportsRepo.getSearchEmployeeReportViewData(empid);
	}
	
	// ---------------BankSubsidary Report------------

	public List<Map<String, String>> getBankSubsidiaryNames(String security_id) {
		return reportsRepo.getBankSubsidiaryNames(security_id);
	}

	public List<Map<String, String>> getBankSubsidiaryReport(String fromdate, String todate, String security_id,
			String banknameaccountno) {
		return reportsRepo.getBankSubsidiaryReport(fromdate, todate, security_id, banknameaccountno);
	}

	public List<Map<String, String>> getOpeningBalanceBankSubsidiaryReport(String fromdate, String banknameaccountno) {
		return reportsRepo.getOpeningBalanceBankSubsidiaryReport(fromdate, banknameaccountno);
	}

	public List<Map<String, String>> getTotalBankSubsidiaryReport(String fromdate, String todate, String security_id,
			String banknameaccountno) {
		return reportsRepo.getTotalBankSubsidiaryReport(fromdate, todate, security_id, banknameaccountno);
	}

	// ---------------CashBankReceipt Report------------

	public List<Map<String, String>> getCashBankDropdown() {
		return reportsRepo.getCashBankDropdown();
	}

	public List<Map<String, String>> getCashBankReceiptsReport(String fromdate, String todate, String cash_bank,
			String security_id) {
		List<Map<String, String>> cashbankReceipts = null;
		if (cash_bank.equals("All")) {
			cashbankReceipts = reportsRepo.getCashBankReceiptsReportAll(fromdate, todate, security_id);
		} else if (!cash_bank.equals("All")) {
			cashbankReceipts = reportsRepo.getCashBankReceiptsReport(fromdate, todate, cash_bank, security_id);
		}
		return cashbankReceipts;
	}

	public List<Map<String, String>> getTotalOfCashBankReceiptsReport(String fromdate, String todate, String cash_bank,
			String security_id) {
		return reportsRepo.getTotalOfCashBankReceiptsReport(fromdate, todate, cash_bank, security_id);
	}
	
	public List<Map<String, String>> getReceiptDataByTransactionID(String payment_receipt_id){
		return reportsRepo.getReceiptDataByTransactionID(payment_receipt_id);
	}
	
	public List<Map<String, String>> getReceiptDataSubheadsByTransactionID(String payment_receipt_id){
		return reportsRepo.getReceiptDataSubheadsByTransactionID(payment_receipt_id);
	}
	
	public 	List<Map<String, String>> getReceiptDataByReceiptNo(String payment_receipt_id, String receiptno){
		return reportsRepo.getReceiptDataByReceiptNo(payment_receipt_id,receiptno);
	}
	
	public List<Map<String, String>> getReceiptDataSubheadsByReceiptNo(String payment_receipt_id, String receiptno){
		return reportsRepo.getReceiptDataSubheadsByReceiptNo(payment_receipt_id,receiptno);
	}
	
	public List<Map<String, String>> getReceiptDataByHeadID(String payment_receipt_id, String headid){
		return reportsRepo.getReceiptDataByHeadID(payment_receipt_id, headid);
	}

	// ---------------CashBankPayment Report------------

	public List<Map<String, String>> getCashBankPaymentReport(String fromdate, String todate, String cash_bank,
			String security_id) {
		List<Map<String, String>> cashbankPayments = null;
		if (cash_bank.equals("All")) {
			cashbankPayments = reportsRepo.getCashBankPaymentReportAll(fromdate, todate, security_id);
		} else if (!cash_bank.equals("All")) {
			cashbankPayments = reportsRepo.getCashBankPaymentReport(fromdate, todate, cash_bank, security_id);
		}
		return cashbankPayments;
	}

	public List<Map<String, String>> getTotalOfCashBankPaymentReport(String fromdate, String todate, String cash_bank,
			String security_id) {
		return reportsRepo.getTotalOfCashBankPaymentReport(fromdate, todate, cash_bank, security_id);
	}
	
	public List<Map<String, String>> getPaymentDataByTransactionID(String payment_receipt_id){
		return reportsRepo.getPaymentDataByTransactionID(payment_receipt_id);
	}

	public List<Map<String, String>> getPaymentDataSubheadsByTransactionID(String payment_receipt_id){
		return reportsRepo.getPaymentDataSubheadsByTransactionID(payment_receipt_id);
	}

	public List<Map<String, String>> getPaymentDataByReceiptNo(String payment_receipt_id, String receiptno){
		return reportsRepo.getPaymentDataByReceiptNo(payment_receipt_id, receiptno);
	}

	public List<Map<String, String>> getPaymentDataSubheadsByReceiptNo(String payment_receipt_id, String receiptno){
		return reportsRepo.getPaymentDataSubheadsByReceiptNo(payment_receipt_id, receiptno);
	}

	public List<Map<String, String>> getPaymentDataByHeadID(String payment_receipt_id, String headid){
		return reportsRepo.getPaymentDataByHeadID(payment_receipt_id, headid);
	}
	
	public List<Map<String, String>> getSubheadsBySeqID(String headid, String subheadseqid){
		return reportsRepo.getSubheadsBySeqID(headid,subheadseqid);
	}

	// -----------------Journal Voucher Report-----------------

	public List<Map<String, String>> getJournalVoucherReport(String fromdate, String todate, String security_id) {
		return reportsRepo.getJournalVoucherReport(fromdate, todate, security_id);
	}

	public List<Map<String, String>> getTotalJournalVoucherReport(String fromdate, String todate, String security_id) {
		return reportsRepo.getTotalJournalVoucherReport(fromdate, todate, security_id);
	}
	
	public 	List<Map<String, String>> getJournalVoucherReportByTransanctionID(String payment_receipt_id){
		return reportsRepo.getJournalVoucherReportByTransanctionID(payment_receipt_id);
	}
	
	public 	List<Map<String, String>> getJournalVoucherReportSubheadsByTransanctionID(String payment_receipt_id){
		return reportsRepo.getJournalVoucherReportSubheadsByTransanctionID(payment_receipt_id);
	}

	// -----------------Journal Register Report ------------------

	public List<Map<String, String>> getJournalRegsiterReport(String fromdate, String todate, String security_id) {
		return reportsRepo.getJournalRegsiterReport(fromdate, todate, security_id);
	}
	
	// -------------------SubLedger Report ---------------------------
	
	public List<Map<String, String>> getSubLedgerReport(String fromdate, String todate, String security_id, String headid,
			String subheadseqid){
		List<Map<String, String>> subledger = null;
		
		if(headid.equals("All") && subheadseqid.equals("All")) {
			subledger = reportsRepo.getSubLedgerReportAll(fromdate,todate,security_id);
		}
		else if(!headid.equals("All") && !subheadseqid.equals("All")) {
			subledger = reportsRepo.getSubLedgerReportByIDs(fromdate,todate,security_id,headid,subheadseqid);
		}
		
		return subledger;
	}

	public List<Map<String, String>> getSubLedgerReportByTransanctionID(String payment_receipt_id){
		return reportsRepo.getSubLedgerReportByTransanctionID(payment_receipt_id);
	}

	public List<Map<String, String>> getSubLedgerReportSubHeadsByTransanctionID(String payment_receipt_id){
		return reportsRepo.getSubLedgerReportSubHeadsByTransanctionID(payment_receipt_id);
	}
	
	//------------------GeneralLedger Report------------------
	
	public List<Map<String, String>> getGeneralLedgerReport(String fromdate, String todate, String security_id, 
			String headid){
		List<Map<String, String>> sub = null;
		if(headid.equals("All")) {
			sub = reportsRepo.getGeneralLedgerReportAll(fromdate,todate,security_id);
		}
		else if(!headid.equals("All")) {
			sub = reportsRepo.getGeneralLedgerReportByHeadID(fromdate,todate,security_id,headid);
		}
		return sub;
	}
	
	public List<Map<String, String>> getOpeningBalGeneralLedgerReport(String todate, String security_id,
			String headid){
		return reportsRepo.getOpeningBalGeneralLedgerReport(todate,security_id,headid);
	}
	
	// ----------------HeadWise Report ------------------------
	
	public List<Map<String, String>> getHeadWiseReportDistrict(String code){
		return reportsRepo.getHeadWiseReportDistrict(code);
	}
	
	public List<Map<String, String>> getHeadwiseReportReceipts(String fromdate, String todate, String headid){
		List<Map<String, String>> receipt = null;
		if(headid.equals("All")) {
			receipt = reportsRepo.getHeadwiseReportReceiptsAll(fromdate, todate);
		}
		else if(!headid.equals("All")) {
			receipt = reportsRepo.getHeadwiseReportReceiptsByHeadID(fromdate, todate, headid);
		}
		return receipt;
	}
	
	public List<Map<String, String>> getHeadwiseReportPayments(String fromdate, String todate, String headid){
		List<Map<String, String>> payment = null;
		if(headid.equals("All")) {
			payment = reportsRepo.getHeadwiseReportPaymentsAll(fromdate, todate);
		}
		else if(!headid.equals("All")) {
			payment = reportsRepo.getHeadwiseReportPaymentsByHeadID(fromdate, todate, headid);
		}
		return payment;
	}
	
	public List<Map<String, String>> getHeadwiseReportJournalVouchers(String fromdate, String todate, String headid){
		List<Map<String, String>> journal = null;
		if(headid.equals("All")) {
			journal = reportsRepo.getHeadwiseReportJournalVouchersAll(fromdate, todate);
		}
		else if(!headid.equals("All")) {
			journal = reportsRepo.getHeadwiseReportJournalVouchersByHeadID(fromdate, todate, headid);
		}
		return journal;
	}
}
