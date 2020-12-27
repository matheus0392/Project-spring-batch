package mnascimento.api.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import mnascimento.api.Domains.Info;

@Service
public interface InfoRepository extends JpaRepository<Info, Long> {

	Optional<Info> findByCPF(String cpf);
}
