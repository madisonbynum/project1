package models;

public class TestTicket {
	
	private int id;
	private int authorID;
	private int resolverID;
	private String status;
	private String type;
	private String submitted;
	private String resolved;
	private double amount;
	private String description;
	
	public TestTicket (int id, int authorID, String status, String type , double amount, String description) {
		super();
		this.id = id;
		this.authorID = authorID;
		this.status = status;
		this.type = type;
		this.amount = amount;
		this.description = description;
	}
	
	public TestTicket(int resolverID, String status) {
		this.resolverID = resolverID;
		this.status = status;
	}
	
	public TestTicket () {
		super();
	}

	public TestTicket(int id, int authorID, int resolverID, String status, String type, String submitted, String resolved,
			double amount, String description) {
		super();
		this.id = id;
		this.authorID = authorID;
		this.resolverID = resolverID;
		this.status = status;
		this.type = type;
		this.submitted = submitted;
		this.resolved = resolved;
		this.amount = amount;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAuthorID() {
		return authorID;
	}

	public void setAuthorID(int authorID) {
		this.authorID = authorID;
	}

	public int getResolverID() {
		return resolverID;
	}

	public void setResolverID(int resolverID) {
		this.resolverID = resolverID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSubmitted() {
		return submitted;
	}

	public void setSubmitted(String submitted) {
		this.submitted = submitted;
	}

	public String getResolved() {
		return resolved;
	}

	public void setResolved(String resolved) {
		this.resolved = resolved;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + authorID;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((resolved == null) ? 0 : resolved.hashCode());
		result = prime * result + resolverID;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((submitted == null) ? 0 : submitted.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestTicket other = (TestTicket) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (authorID != other.authorID)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (resolved == null) {
			if (other.resolved != null)
				return false;
		} else if (!resolved.equals(other.resolved))
			return false;
		if (resolverID != other.resolverID)
			return false;
		if (status != other.status)
			return false;
		if (submitted == null) {
			if (other.submitted != null)
				return false;
		} else if (!submitted.equals(other.submitted))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TestTicket [id=" + id + ", authorID=" + authorID + ", resolverID=" + resolverID + ", status=" + status
				+ ", type=" + type + ", submitted=" + submitted + ", resolved=" + resolved + ", amount=" + amount
				+ ", description=" + description + "]";
	}

	

	
}
