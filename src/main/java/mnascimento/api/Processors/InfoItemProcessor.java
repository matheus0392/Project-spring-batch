package mnascimento.api.Processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import mnascimento.api.Domains.Info;

public class InfoItemProcessor implements ItemProcessor<Info,Info> {

	private static final Logger LOG = LoggerFactory.getLogger(InfoItemProcessor.class);

	@Override
	public Info process(Info item) throws Exception {
		boolean par = false;
		try {
			par = Long.parseLong(item.getCPF()) % 2 == 0;
			if (par) {
				LOG.info("par: " + item);
				return item;
			}
		} catch (NumberFormatException e) {
			LOG.info("NumberFormatException: " + item);
		}

		return null;
	}
}
