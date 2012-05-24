package util;

public enum SongState {

	IN_WANTING("募集中"),
	REACH("リーチ"),
	ESTABLISHED("成立"),
	CANSEL("取り下げ");


	private String name;

	SongState(String name){
		this.name = name;
	}


	public String getName(){
		return this.name;
	}


}
