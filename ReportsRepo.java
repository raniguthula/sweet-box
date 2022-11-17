package in_Apcfss.Apofms.api.ofmsapi.Repositories;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in_Apcfss.Apofms.api.ofmsapi.models.BankDetails;

@Repository
public interface ReportsRepo extends JpaRepository<BankDetails, Integer> {

	// -------------BankBook Report-------------
	// (We can also use security_id='01' in condition instead of user_id---data
	// remains same)

	@Query(value = "select row_number() over(order by date) as sno,payment_receipt_id,to_char(date,'yyyy-mm-dd'),receiptno,name,mode,cheque_dd_receipt_no,receipt_description,no_of_subheads,\r\n"
			+ "banknameaccountno,receiptamount,paymentamount,balance_in_account,transaction_type from payments_receipts \r\n"
			+ "where date between to_date(cast(:fromdate as text),'yyyy-mm-dd') and to_date(cast(:todate as text),'yyyy-mm-dd') \r\n"
			+ "and user_id=:user_id and isdelete='f' order by sno,payment_receipt_id,date,receiptamount,paymentamount", nativeQuery = true)
	List<Map<String, String>> getBankBookReport(String fromdate, String todate, String user_id);
	
	@Query(value = "select sum(paymentamount) from payments_receipts where user_id='DM01' and \r\n"
			+ "date between to_date('01/09/2022','dd/mm/yyyy') and to_date('30/09/2022','dd/mm/yyyy') and isdelete='f'", nativeQuery = true)
	List<Map<String, String>> getBankBookTotalCredit(String fromdate, String todate, String user_id);

	@Query(value = "select sum(receiptamount) from payments_receipts where user_id='DM01' and \r\n"
			+ "date between to_date('01/09/2022','dd/mm/yyyy') and to_date('30/09/2022','dd/mm/yyyy') and isdelete='f' ", nativeQuery = true)
	List<Map<String, String>> getBankBookTotalDebit(String fromdate, String todate, String user_id);


	//--------------Search Employee Report(Cadre Wise)------------
	
	@Query(value = "select  name, code from cgg_master_districts where code=:code order by code", nativeQuery = true)
	List<Map<String, String>> getDistrictName(String code);
	
	@Query(value = "select coalesce(ap1.dist_name,'prdist_other') as prdist, empid, empname, empfather, coalesce((select designation_name from designation where designation=designation_id)) \r\n"
			+ "as present_cadre, dor, othercase as displanary from empdetails_new em left join (select dist_code,dist_name from ap_master  group by  dist_code,dist_name) ap1 \r\n"
			+ "on (ap1.dist_code = em.prdist) where emp_status_id=1 and prdist=:prdist and  designation =:designation "
			+ "and empname=:empname", nativeQuery = true)
	List<Map<String, String>> getSearchEmployeeReport(String empname, int prdist, int designation);
	
	@Query(value = "select empname,empid, de.designation_name, empfather, cr.caste_name as caste, dob, doj, dor,\r\n"
			+ "coalesce(ap1.dist_name,'prdist_other') as prdist,\r\n"
			+ "coalesce((select distinct div_name from ap_master where dist_code=prdist and div_code=prdiv),prdiv_other) as prdiv,\r\n"
			+ "coalesce(ap1.sdiv_name,prsubdiv_other) as prsdiv,coalesce(ap1.mandal_name,prmandal_other) as prmandal,\r\n"
			+ "coalesce((select md.name from cgg_master_districts md where code= lpad(cast(natdist as text),2,'0')) ,natdist_other) as natdist,\r\n"
			+ "coalesce((select distinct div_name from ap_master where dist_code=natdist and div_code=natdiv),natdiv_other) as natdiv,\r\n"
			+ "coalesce(ap2.sdiv_name,natsdiv_other) as natsdiv,\r\n"
			+ "coalesce(ap2.mandal_name,natmandal_other) as natmandal, et.emptype_name as prwork,otherwork as otherdatabasic,othercase as displinary,\r\n"
			+ "case when otherworkarea='0' or otherworkarea is  null  then '-' else cast(otherworkarea as text) end as otherdatabasics,dowpc,\r\n"
			+ "qms.qualificationname as eduappoint,qmst.qualificationname||', Other Qualifications:'||coalesce(other_qualifications,'') as eduafterappoint,\r\n"
			+ "cadreappoint,cadrerefno,cadrerefdate,empaadhar,emppan,emppfuno,prdiv as prdivcode,prsubdiv as prsubdivcode,prsubmandal as prsubmandalcode from empdetails_new em\r\n"
			+ "left join (select dist_code,dist_name,div_code,div_name,sdiv_code,sdiv_name,mandal_code,mandal_name from ap_master  \r\n"
			+ "group by  dist_code,dist_name,div_code,div_name,sdiv_code,sdiv_name,mandal_code,mandal_name) ap1 on (ap1.dist_code = em.prdist and ap1.mandal_code=em.prsubmandal) \r\n"
			+ "left join (select dist_code,dist_name,div_code,div_name,cont_code as sdiv_code,cont_name as sdiv_name,mandal_code,mandal_name from ap_master  \r\n"
			+ "group by  dist_code,dist_name,div_code,div_name,cont_code,cont_name,mandal_code,mandal_name) ap2 on (ap2.dist_code = em.natdist and ap2.mandal_code=em.natmandal) \r\n"
			+ "left join caste_master cr on cr.caste_id=em.caste left join designation de on de.designation_id=em.designation\r\n"
			+ "left join employee_type_inception et on et.emptype_id=cast(em.prwork as integer)  \r\n"
			+ "left join qualificationmaster qms on qms.qualificationid=cast(em.eduappoint as integer)  \r\n"
			+ "left join qualificationmaster qmst on qmst.qualificationid=cast(em.eduafterappoint as integer)  \r\n"
			+ "where emp_status_id=1 and empid=:empid", nativeQuery = true)
	List<Map<String, String>> getSearchEmployeeReportViewData(String empid);
	

	// ---------------BankSubsidary Report------------

	@Query(value = "select banknameaccountno||'-'||branchname as bankname,banknameaccountno from bankdetails where security_id=:security_id", nativeQuery = true)
	List<Map<String, String>> getBankSubsidiaryNames(String security_id);

	@Query(value = "SELECT row_number() over(order by date) as sno,payment_receipt_id,to_char(date,'yyyy-mm-dd') as date,receiptno,name,mode,case when cheque_dd_receipt_no='null' then ''\r\n"
			+ "else cheque_dd_receipt_no end,receipt_description,no_of_subheads,receiptamount,paymentamount,balance_in_account,transaction_type \r\n"
			+ "from((select payment_receipt_id,date,'00' as receiptno,'' \r\n"
			+ "as name,mode,cheque_dd_receipt_no,receipt_description,no_of_subheads,receiptamount,paymentamount,balance_in_account,transaction_type\r\n"
			+ "from manager_payments_receipts m where date between to_date(cast(:fromdate as text),'yyyy/mm/dd') and\r\n"
			+ "to_date(cast(:todate as text),'yyyy/mm/dd') and banknameaccountno=:banknameaccountno and\r\n"
			+ "security_id=:security_id and isdelete='f' order by date) union all \r\n"
			+ "(select payment_receipt_id,date,receiptno,name,mode,cheque_dd_receipt_no,receipt_description,no_of_subheads,receiptamount,paymentamount,\r\n"
			+ "balance_in_account,transaction_type from payments_receipts m where date between to_date(cast(:fromdate as text),'yyyy-mm-dd')\r\n"
			+ "and to_date(cast(:todate as text),'yyyy-mm-dd') and banknameaccountno=:banknameaccountno\r\n"
			+ "and isdelete='f' order by date))as s  order by date,transaction_type desc", nativeQuery = true)
	List<Map<String, String>> getBankSubsidiaryReport(String fromdate, String todate, String security_id,
			String banknameaccountno);

	@Query(value = "SELECT sum(m.receiptamount+p.receiptamount) as receipt_amount,sum(m.paymentamount+p.paymentamount) as payment_amount from (SELECT b.banknameaccountno,\r\n"
			+ "coalesce(sum(receiptamount),0.00) as receiptamount,coalesce(sum(paymentamount),0.00) as paymentamount from bankdetails b \r\n"
			+ "left join  payments_receipts p on date between to_date(cast(:fromdate as text),'yyyy-mm-dd') and to_date(cast(:todate as text),'yyyy-mm-dd') \r\n"
			+ "and p.banknameaccountno=b.banknameaccountno where b.banknameaccountno=:banknameaccountno group by b.banknameaccountno)p  \r\n"
			+ "left join (SELECT b.banknameaccountno,coalesce(sum(receiptamount),0) as receiptamount,coalesce(sum(paymentamount),0) as paymentamount \r\n"
			+ "from bankdetails b left join manager_payments_receipts m on  date between to_date(cast(:fromdate as text),'yyyy-mm-dd') and to_date(cast(:todate as text),'yyyy-mm-dd')  \r\n"
			+ "and m.security_id =:security_id and m.banknameaccountno=b.banknameaccountno where b.banknameaccountno=:banknameaccountno \r\n"
			+ "group by b.banknameaccountno)m on p.banknameaccountno=m.banknameaccountno ", nativeQuery = true)
	List<Map<String, String>> getTotalBankSubsidiaryReport(String fromdate, String todate, String security_id,
			String banknameaccountno);

	@Query(value = "SELECT b.banknameaccountno,(b.balance+(coalesce(receiptamount,0)-coalesce(paymentamount,0))) as total from bankdetails b \r\n"
			+ "left join (SELECT p.banknameaccountno,sum(m.receiptamount+p.receiptamount) as receiptamount,sum(m.paymentamount+p.paymentamount) \r\n"
			+ "as paymentamount from (select b.banknameaccountno,coalesce(receiptamount,0) as receiptamount,\r\n"
			+ "coalesce(paymentamount,0) as paymentamount from bankdetails b \r\n"
			+ "left join (SELECT banknameaccountno,sum(receiptamount) as receiptamount,coalesce(sum(paymentamount),0.00) as paymentamount \r\n"
			+ "from payments_receipts where date < to_date(cast(:fromdate as text),'yyyy-mm-dd') and banknameaccountno=:banknameaccountno \r\n"
			+ "group by banknameaccountno)p on b.banknameaccountno=p.banknameaccountno where b.banknameaccountno=:banknameaccountno)p \r\n"
			+ "left join (SELECT b.banknameaccountno,coalesce(receiptamount,0) as receiptamount,coalesce(paymentamount,0) as paymentamount \r\n"
			+ "from bankdetails b left join (SELECT banknameaccountno,sum(receiptamount) as receiptamount,sum(paymentamount) as paymentamount \r\n"
			+ "from manager_payments_receipts where date < to_date(cast(:fromdate as text),'yyyy-mm-dd') and banknameaccountno=:banknameaccountno \r\n"
			+ "group by banknameaccountno )m on b.banknameaccountno=m.banknameaccountno where b.banknameaccountno=:banknameaccountno)m \r\n"
			+ "on p.banknameaccountno=m.banknameaccountno group by p.banknameaccountno)p on b.banknameaccountno=p.banknameaccountno \r\n"
			+ "where b.banknameaccountno=:banknameaccountno", nativeQuery = true)
	List<Map<String, String>> getOpeningBalanceBankSubsidiaryReport(String fromdate, String banknameaccountno);

	// ---------------CashBankReceipt Report------------

	@Query(value = "SELECT distinct cash_bank FROM payments_receipts", nativeQuery = true)
	List<Map<String, String>> getCashBankDropdown();

	@Query(value = "select payment_receipt_id,to_char(date,'yyyy-mm-dd'),receiptno,name,mode,cheque_dd_receipt_no,receipt_description,no_of_subheads,\r\n"
			+ "banknameaccountno,receiptamount,transaction_type from payments_receipts \r\n"
			+ "where date between to_date(cast(:fromdate as text),'yyyy-mm-dd') and to_date(cast(:todate as text),'yyyy-mm-dd') \r\n"
			+ "and transaction_type='R' and isdelete='f' and security_id=:security_id order by date,receiptamount,paymentamount", nativeQuery = true)
	List<Map<String, String>> getCashBankReceiptsReportAll(String fromdate, String todate, String security_id);

	@Query(value = "select payment_receipt_id,to_char(date,'yyyy-mm-dd'),receiptno,name,mode,cheque_dd_receipt_no,receipt_description,no_of_subheads,\r\n"
			+ "banknameaccountno,receiptamount,transaction_type from payments_receipts \r\n"
			+ "where date between to_date(cast(:fromdate as text),'yyyy-mm-dd') and to_date(cast(:todate as text),'yyyy-mm-dd') \r\n"
			+ "and cash_bank=:cash_bank and transaction_type='R' and isdelete='f' and security_id=:security_id order by date,receiptamount,paymentamount", nativeQuery = true)
	List<Map<String, String>> getCashBankReceiptsReport(String fromdate, String todate, String cash_bank,
			String security_id);

	@Query(value = "select sum(receiptamount) from payments_receipts where date between "
			+ "to_date(cast(:fromdate as text),'yyyy-mm-dd') and to_date(cast(:todate as text),'yyyy-mm-dd') "
			+ "and cash_bank=:cash_bank and transaction_type='R' and security_id=:security_id and isdelete='f' ", nativeQuery = true)
	List<Map<String, String>> getTotalOfCashBankReceiptsReport(String fromdate, String todate, String cash_bank,
			String security_id);
	
	@Query(value = "select payment_receipt_id,receiptno,to_char(date,'dd/mm/yyyy'),cash_bank,banknameaccountno,balance_in_account, receiptamount,name,mode,cheque_dd_receipt_no,\r\n"
			+ "receipt_description,no_of_subheads from payments_receipts where payment_receipt_id=:payment_receipt_id", nativeQuery = true)
	List<Map<String, String>> getReceiptDataByTransactionID(String payment_receipt_id);
	
	@Query(value = "Select  p.headid||'-'||m.headname as headnames,case when subheadseqid='No' then '0' when subheadseqid='more' or subheadseqid='More' then 'More' else \r\n"
			+ "(select subheadname from mstsubheads where subheadseqid=p.subheadseqid) end,amount,subheadseqid,classification  from payments_receipts_subheads p,mstheads m, \r\n"
			+ "payments_receipts pr where m.headid=p.headid and p.payment_receipt_id=pr.payment_receipt_id and p.payment_receipt_id=:payment_receipt_id", nativeQuery = true)
	List<Map<String, String>> getReceiptDataSubheadsByTransactionID(String payment_receipt_id);
	
	@Query(value = "select payment_receipt_id,receiptno,to_char(date,'dd/mm/yyyy'),cash_bank,banknameaccountno,balance_in_account, receiptamount,name,mode,cheque_dd_receipt_no,\r\n"
			+ "receipt_description,no_of_subheads from payments_receipts where payment_receipt_id=:payment_receipt_id and receiptno=:receiptno", nativeQuery = true)
	List<Map<String, String>> getReceiptDataByReceiptNo(String payment_receipt_id, String receiptno);
	
	@Query(value = "Select  p.headid||'-'||m.headname as headnames,case when subheadseqid='No' then '0' when subheadseqid='more' or subheadseqid='More' then 'More' else \r\n"
			+ "(select subheadname from mstsubheads where subheadseqid=p.subheadseqid) end,amount,subheadseqid,classification  from payments_receipts_subheads p,mstheads m, \r\n"
			+ "payments_receipts pr where m.headid=p.headid and p.payment_receipt_id=pr.payment_receipt_id and p.payment_receipt_id=:payment_receipt_id and pr.receiptno=:receiptno", nativeQuery = true)
	List<Map<String, String>> getReceiptDataSubheadsByReceiptNo(String payment_receipt_id, String receiptno);
	
	@Query(value = "Select  p.headid||'-'||m.headname as headnames,case when subheadseqid='No' then '0' when subheadseqid='more' or subheadseqid='More' then 'More' else \r\n"
			+ "(select subheadname from mstsubheads where subheadseqid=p.subheadseqid) end,amount,subheadseqid,classification  from payments_receipts_subheads p,mstheads m, \r\n"
			+ "payments_receipts pr where m.headid=p.headid and p.payment_receipt_id=pr.payment_receipt_id and p.payment_receipt_id=:payment_receipt_id and p.headid=:headid", nativeQuery = true)
	List<Map<String, String>> getReceiptDataByHeadID(String payment_receipt_id, String headid);

	// ---------------CashBankPayment Report------------

	@Query(value = "select payment_receipt_id,to_char(date,'dd/mm/yyyy'),receiptno,name,mode,cheque_dd_receipt_no,receipt_description,no_of_subheads,\r\n"
			+ "banknameaccountno,paymentamount,balance_in_account,transaction_type from payments_receipts \r\n"
			+ "where date between to_date(cast(:fromdate as text),'yyyy-mm-dd') and to_date(cast(:todate as text),'yyyy-mm-dd') \r\n"
			+ "and transaction_type='P' and isdelete='f' and security_id=:security_id order by date,receiptamount,paymentamount", nativeQuery = true)
	List<Map<String, String>> getCashBankPaymentReportAll(String fromdate, String todate, String security_id);

	@Query(value = "select payment_receipt_id,to_char(date,'dd/mm/yyyy'),receiptno,name,mode,cheque_dd_receipt_no,receipt_description,no_of_subheads,\r\n"
			+ "banknameaccountno,paymentamount,balance_in_account,transaction_type from payments_receipts \r\n"
			+ "where date between to_date(cast(:fromdate as text),'yyyy-mm-dd') and to_date(cast(:todate as text),'yyyy-mm-dd') \r\n"
			+ "and cash_bank=:cash_bank and transaction_type='P' and isdelete='f' and security_id=:security_id order by date,receiptamount,paymentamount", nativeQuery = true)
	List<Map<String, String>> getCashBankPaymentReport(String fromdate, String todate, String cash_bank,
			String security_id);

	@Query(value = "select sum(paymentamount) from payments_receipts where date between "
			+ "to_date(cast(:fromdate as text),'yyyy-mm-dd') and to_date(cast(:todate as text),'yyyy-mm-dd') "
			+ "and cash_bank=:cash_bank and transaction_type='P' and isdelete='f' and security_id=:security_id ", nativeQuery = true)
	List<Map<String, String>> getTotalOfCashBankPaymentReport(String fromdate, String todate, String cash_bank,
			String security_id);
	
	@Query(value = "select payment_receipt_id,receiptno,to_char(date,'dd/mm/yyyy'),cash_bank,name,mode,cheque_dd_receipt_no,receipt_description,no_of_subheads,banknameaccountno,\r\n"
			+ "balance_in_account,paymentamount from payments_receipts where transaction_type='P' and payment_receipt_id=:payment_receipt_id", nativeQuery = true)
	List<Map<String, String>> getPaymentDataByTransactionID(String payment_receipt_id);

	@Query(value = "Select  m.headid||'-'||m.headname as HeadNames,(select subheadname from mstsubheads where subheadseqid=p.subheadseqid),amount,subheadseqid  \r\n"
			+ "from payments_receipts_subheads p,mstheads m where payment_receipt_id=:payment_receipt_id and m.headid=p.headid order by m.headid", nativeQuery = true)
	List<Map<String, String>> getPaymentDataSubheadsByTransactionID(String payment_receipt_id);

	@Query(value = "select payment_receipt_id,receiptno,to_char(date,'dd/mm/yyyy'),cash_bank,banknameaccountno,balance_in_account, paymentamount,name,mode,cheque_dd_receipt_no,\r\n"
			+ "receipt_description,no_of_subheads from payments_receipts where payment_receipt_id=:payment_receipt_id and receiptno=:receiptno", nativeQuery = true)
	List<Map<String, String>> getPaymentDataByReceiptNo(String payment_receipt_id, String receiptno);

	@Query(value = "Select  p.headid||'-'||m.headname as headnames,case when subheadseqid='No' then '0' when subheadseqid='more' or subheadseqid='More' then 'More' else \r\n"
			+ "(select subheadname from mstsubheads where subheadseqid=p.subheadseqid) end,amount,subheadseqid,classification  from payments_receipts_subheads p,mstheads m, \r\n"
			+ "payments_receipts pr where m.headid=p.headid and p.payment_receipt_id=pr.payment_receipt_id and p.payment_receipt_id=:payment_receipt_id and pr.receiptno=:receiptno", nativeQuery = true)
	List<Map<String, String>> getPaymentDataSubheadsByReceiptNo(String payment_receipt_id, String receiptno);

	@Query(value = "Select  p.headid||'-'||m.headname as headnames,case when subheadseqid='No' then '0' when subheadseqid='more' or subheadseqid='More' then 'More' else \r\n"
			+ "(select subheadname from mstsubheads where subheadseqid=p.subheadseqid) end,amount,subheadseqid,classification  from payments_receipts_subheads p,mstheads m, \r\n"
			+ "payments_receipts pr where m.headid=p.headid and p.payment_receipt_id=pr.payment_receipt_id and p.payment_receipt_id=:payment_receipt_id and p.headid=:headid order by m.headid", nativeQuery = true)
	List<Map<String, String>> getPaymentDataByHeadID(String payment_receipt_id, String headid);
	
	@Query(value = "SELECT headid,subheadseqid,subheadname,subheadid FROM mstsubheads WHERE headid=:headid and subheadseqid=:subheadseqid", nativeQuery = true)
	List<Map<String, String>> getSubheadsBySeqID(String headid, String subheadseqid);

	// -----------------Journal Voucher Report-----------------

	@Query(value = "select payment_receipt_id,cast(voucher_id as integer),to_char(date,'dd/mm/yyyy') as voucher_date,description,coalesce(debit,0.00) as debit,\r\n"
			+ "coalesce(credit,0.00) as credit,security_id from journal_voucher j\r\n"
			+ "where date between to_date(cast(:fromdate as text),'yyyy-mm-dd') "
			+ "and to_date(cast(:todate as text),'yyyy-mm-dd') and security_id=:security_id and isdelete='f'  \r\n"
			+ "order by voucher_id", nativeQuery = true)
	List<Map<String, String>> getJournalVoucherReport(String fromdate, String todate, String security_id);

	@Query(value = "select coalesce(sum(debit),0.00) as debit,coalesce(sum(credit),0.00) as credit from journal_voucher where date between \r\n"
			+ "to_date(cast(:fromdate as text),'yyyy-mm-dd') and to_date(cast(:todate as text),'yyyy-mm-dd') "
			+ "and security_id=:security_id and isdelete='f'", nativeQuery = true)
	List<Map<String, String>> getTotalJournalVoucherReport(String fromdate, String todate, String security_id);
	
	@Query(value = "select payment_receipt_id,voucher_id,date,description,debit,credit from journal_voucher where "
			+ "payment_receipt_id=:payment_receipt_id", nativeQuery = true)
	List<Map<String, String>> getJournalVoucherReportByTransanctionID(String payment_receipt_id);
	
	@Query(value = "select headid||'-'||(select headname from mstheads where headid=j.headid) as headnames,"
			+ "case subheadid  when 'No' then 'No Subheads' else (select subheadname from mstsubheads ms \r\n"
			+ "where ms.subheadseqid=j.subheadid) end,debit,credit from journal_voucher_heads j "
			+ "where payment_receipt_id=:payment_receipt_id \r\n"
			+ "order by debit desc,headid,credit desc", nativeQuery = true)
	List<Map<String, String>> getJournalVoucherReportSubheadsByTransanctionID(String payment_receipt_id);

	// -----------------Journal Register Report ------------------

	@Query(value = "select  row_number() over(order by voucher_id) as sno,jh.payment_receipt_id,voucher_id as vid,to_char(date,'dd/mm/yyyy'),description,credit,debit,a.headname,a.subheadname\r\n"
			+ "from journal_voucher jh left join\r\n"
			+ "(select payment_receipt_id as id,headid||'-'||(select headname from mstheads where headid=j.headid) as headname,case subheadid  when 'No' then 'No Subheads' else \r\n"
			+ "(select subheadname from mstsubheads ms where ms.subheadseqid=j.subheadid) \r\n"
			+ "end from  journal_voucher_heads j where j.payment_receipt_id=payment_receipt_id ) a on a.id=jh.payment_receipt_id\r\n"
			+ "\r\n" + "where date between to_date(cast(:fromdate as text),'yyyy-mm-dd') \r\n"
			+ "and to_date(cast(:todate as text),'yyyy-mm-dd') and security_id=:security_id and isdelete='f'  order by vid,date", nativeQuery = true)
	List<Map<String, String>> getJournalRegsiterReport(String fromdate, String todate, String security_id);

	// -------------------SubLedger Report ---------------------------
	
	@Query(value = "select row_number() over(order by payment_receipt_id) as sno,payment_receipt_id,description,debit,credit,to_char(date,'dd/mm/yyyy') from ((SELECT payment_receipt_id,(select receipt_description from payments_receipts \r\n"
			+ "where payment_receipt_id =p.payment_receipt_id) as description,case when payment_receipt_id like 'P%' or payment_receipt_id like '2%' then sum(amount) else 0.00 \r\n"
			+ "end as debit,case when payment_receipt_id like 'R%' or payment_receipt_id like '1%' then sum(amount) else 0.00 end as credit,(select date from payments_receipts \r\n"
			+ "where payment_receipt_id =p.payment_receipt_id) as date from payments_receipts_subheads p where  payment_receipt_id not in (select payment_receipt_id \r\n"
			+ "from payments_receipts_incomplete where security_id=:security_id) and  payment_receipt_id in (select payment_receipt_id from payments_receipts where \r\n"
			+ "date between to_date(cast(:fromdate as text),'yyyy-mm-dd') and to_date(cast(:todate as text),'yyyy-mm-dd')) and security_id =:security_id \r\n"
			+ "group by payment_receipt_id) \r\n"
			+ "union all \r\n"
			+ "(SELECT payment_receipt_id,(select receipt_description from payments_receipts where payment_receipt_id =p.payment_receipt_id) \r\n"
			+ "as description,case when payment_receipt_id like 'P%' or payment_receipt_id like '2%' then sum(amount) else 0.00 end as debit,case when payment_receipt_id like 'R%' \r\n"
			+ "or payment_receipt_id like '1%' then sum(amount) else 0.00 end as credit,(select date from payments_receipts where payment_receipt_id =p.payment_receipt_id) as date \r\n"
			+ "from payments_receipts_incomplete p where  payment_receipt_id in (select payment_receipt_id from payments_receipts where date \r\n"
			+ "between to_date(cast(:fromdate as text),'yyyy-mm-dd') and to_date(cast(:todate as text),'yyyy-mm-dd')) and security_id =:security_id \r\n"
			+ "group by payment_receipt_id) \r\n"
			+ "union all \r\n"
			+ "( SELECT payment_receipt_id,(select description from journal_voucher where payment_receipt_id =j.payment_receipt_id),\r\n"
			+ "sum(debit) as debit,sum(credit) as credit,(select date  from journal_voucher where payment_receipt_id =j.payment_receipt_id) as date from journal_voucher_heads j \r\n"
			+ "where payment_receipt_id in (select payment_receipt_id from journal_voucher where date between to_date(cast(:fromdate as text),'yyyy-mm-dd') and to_date(cast(:todate as text),'yyyy-mm-dd')) "
			+ "and security_id =:security_id group by payment_receipt_id) \r\n"
			+ "union all \r\n"
			+ "( SELECT payment_receipt_id,(select description from journal_voucher where payment_receipt_id =j.payment_receipt_id) as description,sum(debit) as debit,sum(credit) \r\n"
			+ "as credit,(select date  from journal_voucher where payment_receipt_id =j.payment_receipt_id) as date from jv_incomplete j where   payment_receipt_id in \r\n"
			+ "(select payment_receipt_id from journal_voucher where date between to_date(cast(:fromdate as text),'yyyy-mm-dd') and to_date(cast(:todate as text),'yyyy-mm-dd')) and security_id =:security_id \r\n"
			+ "group by payment_receipt_id) )abcd order by date,payment_receipt_id ", nativeQuery = true)
	List<Map<String, String>> getSubLedgerReportAll(String fromdate, String todate, String security_id);

	@Query(value = "select row_number() over(order by payment_receipt_id) as sno,payment_receipt_id,description,debit,credit,to_char(date,'dd/mm/yyyy') from ((SELECT payment_receipt_id,(select receipt_description from payments_receipts \r\n"
			+ "where payment_receipt_id =p.payment_receipt_id) as description,case when payment_receipt_id like 'P%' or payment_receipt_id like '2%' then sum(amount) else 0.00 \r\n"
			+ "end as debit,case when payment_receipt_id like 'R%' or payment_receipt_id like '1%' then sum(amount) else 0.00 end as credit,(select date from payments_receipts \r\n"
			+ "where payment_receipt_id =p.payment_receipt_id) as date from payments_receipts_subheads p where  payment_receipt_id not in (select payment_receipt_id \r\n"
			+ "from payments_receipts_incomplete where security_id=:security_id) and  payment_receipt_id in (select payment_receipt_id from payments_receipts where \r\n"
			+ "date between to_date(cast(:fromdate as text),'yyyy-mm-dd') and to_date(cast(:todate as text),'yyyy-mm-dd')) and headid=:headid and p.subheadseqid=:subheadseqid and security_id =:security_id \r\n"
			+ "group by payment_receipt_id) \r\n"
			+ "union all \r\n"
			+ "(SELECT payment_receipt_id,(select receipt_description from payments_receipts where payment_receipt_id =p.payment_receipt_id) \r\n"
			+ "as description,case when payment_receipt_id like 'P%' or payment_receipt_id like '2%' then sum(amount) else 0.00 end as debit,case when payment_receipt_id like 'R%' \r\n"
			+ "or payment_receipt_id like '1%' then sum(amount) else 0.00 end as credit,(select date from payments_receipts where payment_receipt_id =p.payment_receipt_id) as date \r\n"
			+ "from payments_receipts_incomplete p where  payment_receipt_id in (select payment_receipt_id from payments_receipts where date \r\n"
			+ "between to_date(cast(:fromdate as text),'yyyy-mm-dd') and to_date(cast(:todate as text),'yyyy-mm-dd')) and headid=:headid and p.subheadseqid=:subheadseqid and security_id =:security_id \r\n"
			+ "group by payment_receipt_id) \r\n"
			+ "union all \r\n"
			+ "( SELECT payment_receipt_id,(select description from journal_voucher where payment_receipt_id =j.payment_receipt_id),\r\n"
			+ "sum(debit) as debit,sum(credit) as credit,(select date  from journal_voucher where payment_receipt_id =j.payment_receipt_id) as date from journal_voucher_heads j \r\n"
			+ "where payment_receipt_id in (select payment_receipt_id from journal_voucher where date between to_date(cast(:fromdate as text),'yyyy-mm-dd') and to_date(cast(:todate as text),'yyyy-mm-dd')) "
			+ "and headid=:headid and j.subheadid=:subheadseqid and security_id =:security_id group by payment_receipt_id) \r\n"
			+ "union all \r\n"
			+ "( SELECT payment_receipt_id,(select description from journal_voucher where payment_receipt_id =j.payment_receipt_id) as description,sum(debit) as debit,sum(credit) \r\n"
			+ "as credit,(select date  from journal_voucher where payment_receipt_id =j.payment_receipt_id) as date from jv_incomplete j where   payment_receipt_id in \r\n"
			+ "(select payment_receipt_id from journal_voucher where date between to_date(cast(:fromdate as text),'yyyy-mm-dd') and to_date(cast(:todate as text),'yyyy-mm-dd')) and headid=:headid \r\n"
			+ "and j.subheadseqid=:subheadseqid and security_id =:security_id group by payment_receipt_id) )abcd order by date,payment_receipt_id ", nativeQuery = true)
	List<Map<String, String>> getSubLedgerReportByIDs(String fromdate, String todate, String security_id, String headid,
			String subheadseqid);

	@Query(value = "select payment_receipt_id,receiptno,to_char(date,'dd/mm/yyyy') as payment_date,cash_bank,banknameaccountno,name,mode,cheque_dd_receipt_no,receipt_description,no_of_subheads,balance_in_account,\r\n"
			+ "paymentamount from payments_receipts where payment_receipt_id=:payment_receipt_id", nativeQuery = true)
	List<Map<String, String>> getSubLedgerReportByTransanctionID(String payment_receipt_id);

	@Query(value = "Select m.headid||'-'||m.headname as Headnames,case when subheadseqid='No' then '0' when subheadseqid='more' then 'More' else (select subheadname from mstsubheads \r\n"
			+ "where subheadseqid=p.subheadseqid) end,amount,subheadseqid,classification  from payments_receipts_subheads p,mstheads m where payment_receipt_id=:payment_receipt_id\r\n"
			+ "and m.headid=p.headid order by m.headid", nativeQuery = true)
	List<Map<String, String>> getSubLedgerReportSubHeadsByTransanctionID(String payment_receipt_id);

	//------------------GeneralLedger Report------------------
	
	@Query(value = "select row_number() over(order by ps.payment_receipt_id) as sno,m.headid,m.headname,ps.payment_receipt_id as payment_receipt_id ,to_char(date,'dd/mm/yyyy') as date,\r\n"
			+ "receiptno,receipt_description as description,p.balance_in_account,\r\n"
			+ "case when ps.payment_receipt_id like 'P%' or ps.payment_receipt_id like '2%' then amount else '0.00' end as debit,\r\n"
			+ "case when ps.payment_receipt_id like 'R%' or ps.payment_receipt_id like '1%' then amount else '0.00' end as credit,user_id\r\n"
			+ "from mstheads m \r\n"
			+ "inner join payments_receipts_subheads ps on m.headid=ps.headid\r\n"
			+ "inner join payments_receipts p on p.payment_receipt_id=ps.payment_receipt_id\r\n"
			+ "where date between to_date(cast(:fromdate as text),'yyyy-mm-dd') and to_date(cast(:todate as text),'yyyy-mm-dd') and p.security_id=:security_id  \r\n"
			+ "union all\r\n"
			+ "SELECT row_number() over(order by a.payment_receipt_id) as sno,headid,headname,a.payment_receipt_id as payment_receipt_id,to_char(b.date,'dd/mm/yyyy') as date,\r\n"
			+ "b.voucher_id,description,pr.balance_in_account,a.debit as debit,a.credit as credit,'00' as userid from (SELECT m.headid,m.headname ,p.payment_receipt_id,\r\n"
			+ "coalesce(debit,0.00) as debit,coalesce(credit,0.00) as credit from mstheads m \r\n"
			+ "left join journal_voucher_heads p on p.headid=m.headid and p.security_id=:security_id and p.payment_receipt_id in \r\n"
			+ "(select payment_receipt_id from journal_voucher where date between to_date(cast(:fromdate as text),'yyyy-mm-dd') and to_date(cast(:todate as text),'yyyy-mm-dd')) \r\n"
			+ "order by m.headid)a,journal_voucher b,payments_receipts pr where a.payment_receipt_id=b.payment_receipt_id and pr.payment_receipt_id=b.payment_receipt_id \r\n"
			+ "order by headid,date", nativeQuery = true)
	List<Map<String, String>> getGeneralLedgerReportAll(String fromdate, String todate, String security_id);
	
	@Query(value = "select row_number() over(order by ps.payment_receipt_id) as sno,m.headid,m.headname,\r\n"
			+ "ps.payment_receipt_id as payment_receipt_id ,to_char(date,'dd/mm/yyyy') as date,receiptno,\r\n"
			+ "receipt_description as description,p.balance_in_account,\r\n"
			+ "case when ps.payment_receipt_id like 'P%' or ps.payment_receipt_id like '2%' then amount else '0.00' end as debit,\r\n"
			+ "case when ps.payment_receipt_id like 'R%' or ps.payment_receipt_id like '1%' then amount else '0.00' end as credit,user_id from mstheads m \r\n"
			+ "inner join payments_receipts_subheads ps on m.headid=ps.headid\r\n"
			+ "inner join payments_receipts p on p.payment_receipt_id=ps.payment_receipt_id\r\n"
			+ "where m.headid=:headid and date between to_date(cast(:fromdate as text),'yyyy-mm-dd') and to_date(cast(:todate as text),'yyyy-mm-dd') and p.security_id=:security_id \r\n"
			+ "union all\r\n"
			+ "SELECT row_number() over(order by a.payment_receipt_id) as sno,headid,headname,a.payment_receipt_id as payment_receipt_id,to_char(b.date,'dd/mm/yyyy') as date,\r\n"
			+ "b.voucher_id,description,pr.balance_in_account,\r\n"
			+ "a.debit as debit,a.credit as credit,'00' as userid from (SELECT m.headid,m.headname ,p.payment_receipt_id,coalesce(debit,0.00) as debit,\r\n"
			+ "coalesce(credit,0.00) as credit from mstheads m \r\n"
			+ "left join journal_voucher_heads p on p.headid=m.headid and p.security_id=:security_id and p.payment_receipt_id in \r\n"
			+ "(select payment_receipt_id from journal_voucher where date between to_date(cast(:fromdate as text),'yyyy-mm-dd') and to_date(cast(:todate as text),'yyyy-mm-dd')) \r\n"
			+ "and m.headid=:headid order by m.headid)a,journal_voucher b\r\n"
			+ "left join payments_receipts pr on pr.payment_receipt_id=b.payment_receipt_id\r\n"
			+ "where a.payment_receipt_id=b.payment_receipt_id  order by headid,date", nativeQuery = true)
	List<Map<String, String>> getGeneralLedgerReportByHeadID(String fromdate, String todate, String security_id, String headid);

	@Query(value = "select coalesce((select debit-credit),0.00) as ob from (select sum(case when ps.payment_receipt_id like 'P%' or ps.payment_receipt_id like '2%' then amount \r\n"
			+ "else '0.00' end )as debit,sum(case when ps.payment_receipt_id like 'R%' or ps.payment_receipt_id like '1%' then amount else '0.00' end )as credit  \r\n"
			+ "from mstheads m inner join payments_receipts_subheads ps on m.headid=ps.headid  inner join payments_receipts p on p.payment_receipt_id=ps.payment_receipt_id\r\n"
			+ "where m.headid=:headid and date between to_date('2009-04-01','yyyy-mm-dd') and to_date(cast(:todate as text),'yyyy-mm-dd') and p.security_id=:security_id ) a", nativeQuery = true)
	List<Map<String, String>> getOpeningBalGeneralLedgerReport(String todate, String security_id,
			String headid);

	// ----------------HeadWise Report ------------------------
	
	@Query(value = "SELECT row_number() over(order by code) as sno,name FROM cgg_master_districts WHERE code=:code", nativeQuery = true)
	List<Map<String, String>> getHeadWiseReportDistrict(String code);

	@Query(value = "select count(transaction_type) as no_of_receipts,coalesce(sum(receiptamount),0.00) as credit from  payments_receipts where date between \r\n"
			+ "to_date(cast(:fromdate as text),'yyyy-mm-dd') and to_date(cast(:todate as text),'yyyy-mm-dd') and transaction_type='R'", nativeQuery = true)
	List<Map<String, String>> getHeadwiseReportReceiptsAll(String fromdate, String todate);

	@Query(value = "select count(transaction_type) as no_of_receipts,coalesce(sum(receiptamount),0.00) as credit from  payments_receipts pr\r\n"
			+ "left join payments_receipts_subheads ps on ps.payment_receipt_id=pr.payment_receipt_id\r\n"
			+ "left join mstheads mst on mst.headid=ps.headid\r\n"
			+ "where date between to_date(cast(:fromdate as text),'yyyy-mm-dd') and to_date(cast(:todate as text),'yyyy-mm-dd') and transaction_type='R' "
			+ "and mst.headid=:headid", nativeQuery = true)
	List<Map<String, String>> getHeadwiseReportReceiptsByHeadID(String fromdate, String todate, String headid);

	@Query(value = "select count(transaction_type) as no_of_payments,coalesce(sum(paymentamount),0.00) as debit from  payments_receipts where date between \r\n"
			+ "to_date(cast(:fromdate as text),'yyyy-mm-dd') and to_date(cast(:todate as text),'yyyy-mm-dd') and transaction_type='P'", nativeQuery = true)
	List<Map<String, String>> getHeadwiseReportPaymentsAll(String fromdate, String todate);

	@Query(value = "select count(transaction_type),coalesce(sum(paymentamount),0.00) from  payments_receipts pr\r\n"
			+ "left join payments_receipts_subheads ps on ps.payment_receipt_id=pr.payment_receipt_id\r\n"
			+ "left join mstheads mst on mst.headid=ps.headid\r\n"
			+ "where date between \r\n"
			+ "to_date(cast(:fromdate as text),'yyyy-mm-dd') and to_date(cast(:todate as text),'yyyy-mm-dd') and transaction_type='P' "
			+ "and mst.headid=:headid", nativeQuery = true)
	List<Map<String, String>> getHeadwiseReportPaymentsByHeadID(String fromdate, String todate, String headid);

	@Query(value = "select count(distinct(payment_receipt_id)) as no_of_journalvouchers,coalesce(sum(debit),0.00) as jvdebit,"
			+ "coalesce(sum(credit),0.00) as jvcredit from journal_voucher_heads j\r\n"
			+ "where j.payment_receipt_id in (select payment_receipt_id from journal_voucher where date between \r\n"
			+ "to_date(cast(:fromdate as text),'yyyy-mm-dd') and to_date(cast(:todate as text),'yyyy-mm-dd'))", nativeQuery = true)
	List<Map<String, String>> getHeadwiseReportJournalVouchersAll(String fromdate, String todate);

	@Query(value = "select count(distinct(payment_receipt_id)) as no_of_journalvouchers,coalesce(sum(debit),0.00) as jvdebit,"
			+ "coalesce(sum(credit),0.00) as jvcredit from journal_voucher_heads j\r\n"
			+ "where j.payment_receipt_id in (select payment_receipt_id from journal_voucher where date between \r\n"
			+ "to_date(cast(:fromdate as text),'yyyy-mm-dd') and to_date(cast(:todate as text),'yyyy-mm-dd'))  and headid=:headid", nativeQuery = true)
	List<Map<String, String>> getHeadwiseReportJournalVouchersByHeadID(String fromdate, String todate, String headid);
}
