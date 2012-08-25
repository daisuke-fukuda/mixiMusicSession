package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;
import util.Const;
import util.SongState;
import util.Util;

/**
 * @author moyashi
 *
 */
@Entity
public class Song extends Model {


	public Song(Event event) {
		this.event = event;
		for( EventPart wantedPart : event.wantedParts) {
			Part part = new Part();
			part.eventPart = wantedPart;
			parts.add(part);
		}
	}


	// タイトル
	@MaxSize(255)
	@Required
	public String title;

	@MaxSize(255)
	public String singer;

	@ManyToOne
	public Event event;

	@OneToMany(cascade=CascadeType.ALL)
	public List<Part> parts = new ArrayList();

	@MaxSize(255)
	public String remarks;

	public SongState state;


	/**
	 * @param eventPartId EventPartのID
	 * @return eventPartIdにひもづくPart
	 */
	public Part findPartByEventPartId(Long eventPartId) {

		for (Part part : parts) {
			if (part.eventPart.id.equals(eventPartId)) {
				return part;
			}
		}
		return null;
	}


	/**
	 * 募集パートを置き換える
	 * @param 募集するパートのEventPartのID
	 */
	public void replaceRecruitmentParts(List<Long> eventPartIds){

		// 一旦リセット
		for (Part part : parts){
			part.is_in_recruitment = false;
		}

		//募集パートの編集
		if (eventPartIds != null) {
			for (Long id : eventPartIds){
				Part part = findPartByEventPartId(id) ;
				if ( part != null ) {
					part.is_in_recruitment = true;
				}
			}
		}

	}


	/**
	 * 参加するパートをセットする
	 * @param 参加するパートのEventPartのID
	 */
	public void setParticipateParts(List<Long> eventPartIds){

		// 参加パートの編集
		if (eventPartIds != null) {
			User user = Util.getLoginUser();

			for (Long id : eventPartIds) {
				Part part = findPartByEventPartId(id);
				if ( part != null) {
					part.participant = user;
				}
			}
		}
	}



	/**
	 * eventPartIdを複数指定して、パート名を取得する。
	 * 複数の場合はカンマ区切り。
	 *
	 * @param eventPartIds
	 * @return 引数にひもづくパート名
	 */
	public String findPartsNames(List<Long> eventPartIds){


		if (eventPartIds != null) {
			List<String> partNames = new ArrayList();

			for (Long id : eventPartIds) {
				Part part = findPartByEventPartId(id);
				if (part != null) {
					partNames.add(part.eventPart.partName);
				}
			}
			return Util.list2StringWithoutBrackets(partNames);
		}

		return "";
	}

	/**
	 * @return 募集中パートの文字列。複数の場合はカンマ区切り。
	 */
	public String findIsInRecruitmentPartsName(){

		List<String> partNames = new ArrayList();

		for (Part part : parts){
			if (part.isWanting()){
				partNames.add(part.eventPart.partName);
			}
		}

		return Util.list2StringWithoutBrackets(partNames);
	}

	/**
	 * 参加者をセットする
	 * @param 参加者のUserId
	 */
	public void setParticipants(List<String> participantIds) {

		if (participantIds != null) {
			if ( participantIds.size() != parts.size()) {
				throw new IllegalArgumentException();
			}

			for (int i = 0; i < parts.size(); i++) {
				String id = participantIds.get(i);

				if (id.equals(Const.USER_ID_DUMMY)) {
					parts.get(i).participant = null;
				} else {
					// 参加者変更
					User user = User.findById(id);
					parts.get(i).participant = user;
				}
			}
		}
	}


	@Override
	public String toString() {

		return title;
	}

}
