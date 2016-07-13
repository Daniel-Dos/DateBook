package br.pos.trabalho.appdatebook2.model;

/**
 * Classe Compromisso
 * 
 * @author paulo <paulo.rogeriobr@gmail.com>
 * @access public
 * 
 */
public class Compromisso {

	/**
	 * 
	 */
	private long id;
	private String titulo;
	private String data;
	private String horaInicio;
	private String horaTermino;

	/**
	 * construtor
	 */
	public Compromisso() {

	}

	/**
	 * retorna o id
	 * 
	 * @return
	 */
	public long getId() {
		return id;
	}

	/**
	 * define o id
	 * 
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Retorna o titulo do compromisso
	 * 
	 * @return
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * Define o titulo do compromisso
	 * 
	 * @param titulo
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * Retorna a data do compromisso
	 * 
	 * @return
	 */
	public String getData() {
		return data;
	}

	/**
	 * Define a data do compromisso
	 * @param data
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * Retorna a hora inicio do compromisso
	 * @return
	 */
	public String getHoraInicio() {
		return horaInicio;
	}

	/**
	 * Define a hora do inicio do compromisso
	 * @param horaInicio
	 */
	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	/**
	 * Define a hora do termino do compromisso
	 * @return
	 */
	public String getHoraTermino() {
		return horaTermino;
	}

	/**
	 * Define a hora do termino do compromisso
	 * @param horaTermino
	 */
	public void setHoraTermino(String horaTermino) {
		this.horaTermino = horaTermino;
	}
}