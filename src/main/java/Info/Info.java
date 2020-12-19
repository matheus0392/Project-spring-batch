package Info;

public class Info {

	private Long id;
	private String name;
	private String CPF;

	public Info(Long id, String name, String cPF) {
		super();
		this.id = id;
		this.name = name;
		CPF = cPF;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}

}
