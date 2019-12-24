package com.game.utils;

import java.util.Objects;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * ModelApiResponse
 */
@Validated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModelApiResponse {
	@JsonProperty("codigo")
	private String codigo = null;

	@JsonProperty("descripcion")
	private String descripcion = null;

	public ModelApiResponse codigo(String codigo) {
		this.codigo = codigo;
		return this;
	}

	/**
	 * Get codigo
	 * 
	 * @return codigo
	 **/

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public ModelApiResponse descripcion(String descripcion) {
		this.descripcion = descripcion;
		return this;
	}

	/**
	 * Get descripcion
	 * 
	 * @return descripcion
	 **/

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ModelApiResponse _apiResponse = (ModelApiResponse) o;
		return Objects.equals(this.codigo, _apiResponse.codigo)
				&& Objects.equals(this.descripcion, _apiResponse.descripcion);
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo, descripcion);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ModelApiResponse {\n");

		sb.append("    codigo: ").append(toIndentedString(codigo)).append("\n");
		sb.append("    descripcion: ").append(toIndentedString(descripcion)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
