package sng.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cols {

	final List<String> colums = new ArrayList<String>();

	private boolean include = true;

	public boolean include() {
		return include;
	}

	public List<String> getColums() {
		return Collections.unmodifiableList(colums);
	}

	public static Cols c() {
		return new Cols();
	}

	public Cols includes(String... names) {
		if (!this.include) {
			this.include = true;
			this.colums.clear();
		}
		if (names != null) {
			for (String name : names)
				this.colums.add(name);
		}
		return this;
	}

	public Cols ignores(String... names) {
		if (this.include) {
			this.include = false;
			this.colums.clear();
		}
		if (names != null) {
			for (String name : names)
				this.colums.add(name);
		}
		return this;
	}
}
