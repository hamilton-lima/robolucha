package com.robolucha.runner;

import java.util.List;

/**
 * list para manipulacao multithread, exemplo de uso da classe
 * 
 * <code>
  	int count = 0;
	while (pos < queue.size()) {
		String current = (String) queue.get(pos++);
		if (current == null) {
			continue;
		}
	}
	</code>
 * 
 * @author Hamilton Lima
 * TODO: definir objeto que eh DONO da lista e somente permitir que este objeto remova 
 */
public class SafeList {

	private List list;

	public SafeList(List list) {
		this.list = list;
	}
	
	public void clear(){
		this.list.clear();
	}

	public synchronized void add(Object o) {
		int pos = 0;
		while (pos < this.list.size()) {

			Object current = get(pos);
			if (current == null) {
				this.list.set(pos, o);
				return;
			}
			pos++;
		}

		this.list.add(o);
	}

	/**
	 * somente um thread pode remover
	 */
	public void remove(int pos) {
		if (pos < list.size()) {
			this.list.set(pos, null);
		}
	}

	public int size() {
		return this.list.size();
	}

	public Object get(int n) {
		if (n >= list.size()) {
			return null;
		}

		Object result = null;

		try {
			result = list.get(n);
		} catch (Exception e) {
		}

		return result;
	}

}
