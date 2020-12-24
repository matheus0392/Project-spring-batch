package mnascimento.api;

import org.springframework.batch.core.SkipListener;

import mnascimento.api.Domains.Order;
import mnascimento.api.Domains.TrackedOrder;

public class CustomSkipListener implements SkipListener<Order, TrackedOrder> {

	@Override
	public void onSkipInRead(Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSkipInWrite(TrackedOrder item, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSkipInProcess(Order item, Throwable t) {

		System.out.println("skipping processing of the item id: " + item.getOrderId());

	}

}
