package util;

import java.util.ArrayList;
import java.util.Iterator;


public class CustomList<E> extends ArrayList<E>{

	@Override
	public String toString(){
        Iterator<E> i = iterator();
    	if (! i.hasNext())
    	    return "";
    	StringBuilder sb = new StringBuilder();
    	for (;;) {
    	    E e = i.next();
    	    sb.append(e);
    	    if (! i.hasNext())
    		return sb.toString();
    	    sb.append(", ");
    	}

	}

}