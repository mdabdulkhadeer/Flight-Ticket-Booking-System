package com.springBoot.flightBooking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.springBoot.flightBooking.dao.PaymentDao;
import com.springBoot.flightBooking.dto.PaymentMode;
import com.springBoot.flightBooking.dto.PaymentStatus;
import com.springBoot.flightBooking.dto.ResponseStructure;
import com.springBoot.flightBooking.entity.Payment;
import com.springBoot.flightBooking.exception.NoRecordAvailableException;

@Service
public class PaymentService {
	
	@Autowired
	private PaymentDao paymentDao;
	
	public ResponseEntity<ResponseStructure<Payment>> addPayment(Payment payment) {
		ResponseStructure<Payment> response = new ResponseStructure<Payment>();

		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Payment Record Was Saved Successfully...✅");
		response.setData(paymentDao.addPayment(payment));

		return new ResponseEntity<ResponseStructure<Payment>>(response, HttpStatus.CREATED);

	}
	
	//get all payment
		public ResponseEntity<ResponseStructure<List<Payment>>>getAllPayment(){
			ResponseStructure<List<Payment>>response=new ResponseStructure<List<Payment>>();
			List<Payment>payments=paymentDao.getAllPayment();
			
			if(payments.isEmpty())
				throw new NoRecordAvailableException("payment records are not available...😞");
			
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Here your payment details...👇🏽");
			response.setData(payments);
			
			return new ResponseEntity<ResponseStructure<List<Payment>>>(response,HttpStatus.OK);
		}
		
		
		//get payment by id 
		public ResponseEntity<ResponseStructure<Payment>>getPaymentById(Integer id){
			ResponseStructure<Payment>response=new ResponseStructure<Payment>();
			Optional<Payment>payment=paymentDao.getPaymentById(id);
			
			if(payment.isEmpty())
				throw new NoRecordAvailableException("No record available on id '"+id+"'...⚠️");
			
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Here is your Payment Record...✌️");
			response.setData(payment.get());
			
			return new ResponseEntity<ResponseStructure<Payment>>(response,HttpStatus.OK);
			
		}
		
		//get payment by status
		public ResponseEntity<ResponseStructure<List<Payment>>>getPaymentByStatus(PaymentStatus status){
			ResponseStructure<List<Payment>>response=new ResponseStructure<List<Payment>>();
			List<Payment>payments=paymentDao.getPaymentByStatus(status);
			
			if(payments.isEmpty())
				throw new NoRecordAvailableException("Payment records are not available in the status '"+status+"'...😞");
			
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Here is your payment record...✌️");
			response.setData(payments);
			
			return new ResponseEntity<ResponseStructure<List<Payment>>>(response,HttpStatus.OK);
			
		}
		
		//get payment greater then particular amount
		public ResponseEntity<ResponseStructure<List<Payment>>>getPaymentWhereAmountGreaterThan(double amount){
			
			ResponseStructure<List<Payment>>response=new ResponseStructure<List<Payment>>();
			List<Payment>payments=paymentDao.getPaymentWhereAmountGreaterThan(amount);
			
			if(payments.isEmpty())
				throw new NoRecordAvailableException("Payment records are not available greater then 'Rs."+amount+"'...😞");
			
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Here is your payment Details...👇🏽");
			response.setData(payments);
			
			return new ResponseEntity<ResponseStructure<List<Payment>>>(response,HttpStatus.OK);
		}
		
		//get payment by payment mode
		public ResponseEntity<ResponseStructure<List<Payment>>>getPaymentByMode(PaymentMode mode){
			
			ResponseStructure<List<Payment>>response=new ResponseStructure<List<Payment>>();
			List<Payment>payments=paymentDao.getPaymentByMode(mode);
			
			if(payments.isEmpty())
				throw new NoRecordAvailableException("Payment records are not available in the Mode '"+mode+"'...😵");
			
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Here is your payment details...👇🏽");
			response.setData(payments);
			
			return new ResponseEntity<ResponseStructure<List<Payment>>>(response,HttpStatus.OK);
		}
		
		//update payment status by taking status, payment id
		public ResponseEntity<ResponseStructure<Payment>>updatePaymentStatus(Integer id,PaymentStatus status){
			ResponseStructure<Payment> response=new ResponseStructure<Payment>();
			Optional<Payment> payment=paymentDao.getPaymentById(id);
			
			if(payment.isEmpty())
				throw new NoRecordAvailableException("Payment not exist on this id '"+id+"'...⚠️");
			
			payment.get().setStatus(status);
			
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Updated successfully...✌️");
			response.setData(paymentDao.addPayment(payment.get()));
			
			return new ResponseEntity<ResponseStructure<Payment>>(response,HttpStatus.OK);

			
		}
		
		
		//get payment by pagination and sorting
		public ResponseEntity<ResponseStructure<Page<Payment>>>getPaymentByPageAndSort(Integer pageNumber,Integer pageSize,String field){
			ResponseStructure<Page<Payment>>response=new ResponseStructure<Page<Payment>>();
			Page<Payment>payments=paymentDao.getPaymentByPageAndSort(pageNumber, pageSize, field);
			
			if(payments.isEmpty())
				throw new NoRecordAvailableException("Records are not avilable in db...⚠️");
			
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Here your Payment records paged and sorted Based on '"+ field +"'...✌️");
			response.setData(payments);
			
			return new ResponseEntity<ResponseStructure<Page<Payment>>>(response,HttpStatus.OK);
		}


}
