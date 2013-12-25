package br.ufrn.musica.calendala.mandala;

import java.util.ArrayList;

import br.ufrn.musica.calendala.util.CircularArrayList;

/**
 * @author Felipe Cortez de S�
 * @version 0.2
 * @since 0.1
 */

public class Ring {
	private CircularArrayList<Slice> slices;
	private int subdivisions;
	private int totalDivs;

	public enum Direction {CW, CCW, UP, DOWN, NONE}
	
	public Ring() {
		slices = new CircularArrayList<Slice>(); // Maybe change to ArrayList later
		subdivisions = 12;
		totalDivs = subdivisions;
		for(int i = 0; i < subdivisions; i++) {
			Slice s = new Slice(i, this);
			slices.add(s);
		}
	}

	public Ring(int subdivisions) {
		slices = new CircularArrayList<Slice>();
		this.subdivisions = subdivisions;
		totalDivs = subdivisions;
		for(int i = 0; i < subdivisions; i++) {
			Slice s = new Slice(i, this);
			slices.add(s);
		}
	}
	
	public int getSubdivisions() {
		return subdivisions;
	}

	public void setSubdivisions(int subdivisions) {
		this.subdivisions = subdivisions;
	}

	public int getTotalDivs() {
		return totalDivs;
	}

	public void setTotalDivs(int totalDivs) {
		this.totalDivs = totalDivs;
	}
	
	public void addMerge(int start, int size) {
		for(int i = 0; i < size; i++) {
			slices.get(start + i).setStart(start);
		}
		slices.get(start).setMergeSize(size);
		totalDivs -= size - 1;
	}

	public void mergeCells(int start, int quantity, Direction direction) {
		int size = 0;
		int slice = start;
		if(direction == Direction.CW) {
			int i = 0;
			do {
				size += slices.get(slice).getMergeSize();
				slice = slices.get(slice).getNext();
				i++;
			} while(i < quantity);
			addMerge(start, size);
		} else if(direction == Direction.CCW) {
			// Messy but it works! Fix this later
			int i = 0;
			do {
				size += slices.get(slice).getMergeSize();
				slice = slices.get(slice).getPrev();
				i++;
			} while(i < quantity);
			slice = slices.get(slice).getNext();
			addMerge(slice, size);
		}
		Mandala.getInstance().setSelectionRange(0);
	}
	
	public void rotate(Direction direction) {
	}
	
	public CircularArrayList<Slice> getSlices() {
		return slices;
	}
}