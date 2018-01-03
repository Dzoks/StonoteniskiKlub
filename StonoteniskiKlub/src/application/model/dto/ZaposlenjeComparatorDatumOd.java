package application.model.dto;

import java.util.Comparator;

public class ZaposlenjeComparatorDatumOd implements Comparator<ZaposlenjeDTO>{

	@Override
	public int compare(ZaposlenjeDTO o1, ZaposlenjeDTO o2) {
		return o2.getDatumOd().compareTo(o1.getDatumOd());
	}

}
