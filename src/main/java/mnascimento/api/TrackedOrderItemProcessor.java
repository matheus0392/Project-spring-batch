package mnascimento.api;

import java.util.UUID;

import org.springframework.batch.item.ItemProcessor;

import mnascimento.api.Domains.Order;
import mnascimento.api.Domains.TrackedOrder;

public class TrackedOrderItemProcessor implements ItemProcessor<Order, TrackedOrder> {

	@Override
	public TrackedOrder process(Order item) throws Exception {
		TrackedOrder trackedOrder = new TrackedOrder(item);

		System.out.println("Order id: " + item.getOrderId());
		System.out.println("Order Thread: " + Thread.currentThread().getName());
		// trackedOrder.setFreeShipping(freeShipping);
		trackedOrder.setTrackingNumber(getTrackingNumber());

		return trackedOrder;
	}

	private String getTrackingNumber() throws OrderProcessingException {

		if (Math.random() < .05) {
			throw new OrderProcessingException();
		}
		return UUID.randomUUID().toString();
	}

}
