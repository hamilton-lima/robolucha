package com.robolucha.game.vo;

public class ScoreVO implements Comparable<ScoreVO> {

	private Long id;
	private String name;
	private int k;
	private int d;
	private int score;

	public ScoreVO() {
		this.k = 0;
		this.d = 0;
		this.score = 0;
	}

	/**
	 * for renaming
	 * 
	 * @param previous
	 * @param name
	 */
	public ScoreVO(ScoreVO previous, String name) {
		this.id = previous.id;
		this.name = name;
		this.k = previous.k;
		this.d = previous.d;
		this.score = previous.score;
	}
	
	public ScoreVO(Long id, String name) {
		this();
		this.id = id;
		this.name = name;
	}

	// for tests only
	ScoreVO(Long id, String name, int k, int d, int score) {
		this.id = id;
		this.name = name;
		this.k = k;
		this.d = d;
		this.score = score;
	}

	//TODO: move this to other class to sort the Score
		public int compareTo(ScoreVO o) {
		// TODO Auto-generated method stub
		int sub = o.score - this.score;

		// iguais realiza desempate
		if (sub != 0) {
			return sub;
		} else {

			// desempate pelo numero de kill
			sub = o.k - this.k;
			if (sub != 0) {
				return sub;
			} else {

				// desempate por quem morreu menos
				sub = this.d - o.d;
				if (sub != 0) {
					return sub;
				} else {

					// se nada der certo o mais velhor tem prioridade :)
					return (int) (this.id - o.id);
				}
			}

		}
	}

	public int getScore() {
		return score;
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

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}

	public int getD() {
		return d;
	}

	public void setD(int d) {
		this.d = d;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
