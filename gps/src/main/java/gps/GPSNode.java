package gps;

import gps.api.GPSState;

public class GPSNode {

	private GPSState state;

	private GPSNode parent;

	private Integer cost;

    private int depth;

	public GPSNode(GPSState state, Integer cost) {
		super();
		this.state = state;
		this.cost = cost;
        this.depth = 0;
	}

	public GPSNode getParent() {
		return parent;
	}

	public void setParent(GPSNode parent) {
		this.parent = parent;
        this.depth = parent.getDepth() + 1;
	}

	public GPSState getState() {
		return state;
	}

	public Integer getCost() {
		return cost;
	}

	@Override
	public String toString() {
		return state.toString();
	}

	public String getSolution() {
		if (this.parent == null) {
			return this.state.toString();
		}
		return this.parent.getSolution() + "\n" + this.state;
	}

    public int getDepth() {
        return this.depth;
    }
}
