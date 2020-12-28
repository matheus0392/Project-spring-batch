package dev.msnascimento.api.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.msnascimento.api.Domains.Info;
import dev.msnascimento.api.Repository.InfoRepository;

@Service
public class InfoService {

	@Autowired
	private InfoRepository repository;

	public List<Info> GetAll() {
		return repository.findAll();
	}

	public Info save(Info info) throws Exception {

		Long.parseLong(info.getCPF()); // throws NumberFormatException

		if (repository.findById(info.getId()).isPresent()) {
			throw new Exception("id já salvo");
		}

		repository.save(info);

		return info;
	}

	public Info get(String cpf) {
		Optional<Info> info = repository.findByCPF(cpf);
		if (info.isPresent()) {
			return info.get();
		}
		return null;
	}
}
