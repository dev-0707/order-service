package workshop.order.service.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "orders")
@Builder
public class Order implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9003029390721330304L;
	@Id
	@GeneratedValue
	private UUID id;
	private Date createDate;
	private Double total;
	private String status;

}
