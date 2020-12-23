package mnascimento.api;

import org.springframework.batch.item.ItemProcessor;

import mnascimento.api.Domains.Order;
import mnascimento.api.Domains.TrackedOrder;

public class FreeShippingItemProcessor implements ItemProcessor<TrackedOrder, TrackedOrder> {

	@Override
	public TrackedOrder process(TrackedOrder item) throws Exception {

		if (item.getCost().doubleValue() > 80.0) {
			item.setFreeShipping(true);
		}

		return item;
	}

}
