package uo.ri.cws.application.service.mechanic;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.service.BusinessException;

/**
 * This service is intended to be used by the Manager It follows the ISP
 * principle (@see SOLID principles from RC Martin)
 */
public interface MechanicCrudService {

	/**
     * @formatter:off
     * 
     * Add a new mechanic to the system with the data specified in the DTO. The
     * id value will be ignored as it is generated here.
     * 
     * @param mecanich A DTO containing info to be added
     * @return DTO with the id value set to the UUID generated
     * 
     * @throws IllegalArgumentException when 
     * 	- the argument is null or 
     * 	- dni is null or empty
     * 
     * @throws BusinessException if 
     * 	- there already exists another mechanic with the same dni
     * 
     * @formatter:on
     */
	MechanicDto addMechanic(MechanicDto mechanic) throws BusinessException;

	/**
     * @formatter:off
     * 
     * @param idMechanic the id of the mechanic to be deleted
     * @throws BusinessException if 
     * 	- the mechanic does not exist or 
     * 	- there are work orders registered for this mechanic
     * 
     * @throws IllegalArgumentException when argument is null or empty string
     * 
     * @formatter:on
     */
	void deleteMechanic(String idMechanic) throws BusinessException;

	/**
     * @formatter:off
     * 
     * Updates values for the mechanic specified by the id field, just name and
     * surname will be updated
     * 
     * @param mechanic A DTO identifying the mechanic to update by the field id,
     *   and the data to update in name and surname
     *   
     * @throws BusinessException if the mechanic does not exist
     * @throws IllegalArgumentException when 
     * 	- the argument is null or any of the fields (id, dni, name, surname) 
     * 		are null or empty
     * 
     * @formatter:on
     */
	void updateMechanic(MechanicDto mechanic) throws BusinessException;

	/**
     * @formatter:off
     * 
     * @param idMechanic The id of the mechanic to find
     * @return the DTO for the mechanic or null if there is none with the id
     * @throws IllegalArgumentException when argument is null or empty string
     * @throws BusinessException DOES NOT
     * 
     * @formatter:on
     */
	Optional<MechanicDto> findMechanicById(String idMechanic)
			throws BusinessException;

	/**
     * @formatter:off
     * 
     * @param dniMechanic The dni of the mechanic to find
     * @return the DTO for the mechanic or null if there is none with this dni
     * @throws IllegalArgumentException when argument is null or empty
     * @throws BusinessException DOES NOT
     * 
     * @formatter:on
     */
	Optional<MechanicDto> findMechanicByDni(String dniMechanic)
			throws BusinessException;

	/**
     * @formatter:off

     * @return the list of all mechanics registered in the system without
     * 	regarding their contract status or if they have contract or not. 
     * 	It might be an empty list if there is no mechanic
     *
     * @throws BusinessException DOES NOT
     * 
     * @formatter:on
     */
	List<MechanicDto> findAllMechanics() throws BusinessException;

	public static class MechanicDto {
		public String id;
		public Long version;

		public String dni;
		public String name;
		public String surname;
	}
}
