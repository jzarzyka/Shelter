package zarzyka.jagoda.shelter.address.mapper;

import zarzyka.jagoda.shelter.address.api.AddressDTO;
import zarzyka.jagoda.shelter.address.model.AddressEntity;

public class AddressMapper {

    public AddressEntity fromDto(AddressDTO addressDTO) {
        return AddressEntity.builder()
                .city(addressDTO.getCity())
                .country(addressDTO.getCountry())
                .houseNumber(addressDTO.getHouseNumber())
                .zipCode(addressDTO.getZipCode())
                .street(addressDTO.getStreet())
                .build();
    }

    public AddressDTO toDto(AddressEntity addressEntity) {
        return AddressDTO.builder()
                .city(addressEntity.getCity())
                .country(addressEntity.getCountry())
                .houseNumber(addressEntity.getHouseNumber())
                .zipCode(addressEntity.getZipCode())
                .street(addressEntity.getStreet())
                .build();
    }
}
