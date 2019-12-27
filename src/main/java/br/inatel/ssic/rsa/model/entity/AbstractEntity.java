package br.inatel.ssic.rsa.model.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@MappedSuperclass
@Getter
@Setter
public abstract class AbstractEntity <ID extends Serializable> implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private ID id;
}
