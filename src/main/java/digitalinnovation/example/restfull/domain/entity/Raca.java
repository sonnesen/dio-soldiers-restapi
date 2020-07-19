package digitalinnovation.example.restfull.domain.entity;

public enum Raca {

    HUMANO("humano"),
    ELFO("elfo"),
    ORC("orc"),
    ANAO("anão");

    private String value;

    Raca(String value) {
	this.value = value;
    }

    public String getValue() {
	return value;
    }
    
}
