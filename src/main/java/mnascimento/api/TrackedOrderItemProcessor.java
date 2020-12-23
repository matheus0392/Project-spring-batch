package mnascimento.api;

import java.util.UUID;

import org.springframework.batch.item.ItemProcessor;

import mnascimento.api.Domains.Order;
import mnascimento.api.Domains.TrackedOrder;

public class TrackedOrderItemProcessor implements ItemProcessor<Order, TrackedOrder> {

	@Override
	public TrackedOrder process(Order item) throws Exception {
		TrackedOrder trackedOrder= new TrackedOrder(item);

		// trackedOrder.setFreeShipping(freeShipping);
		trackedOrder.setTrackingNumber(UUID.randomUUID().toString());
		
		return trackedOrder;
	}

}
