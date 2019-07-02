package zarzyka.jagoda.shelter.adoption.mapper;

import zarzyka.jagoda.shelter.address.mapper.AddressMapper;
import zarzyka.jagoda.shelter.adoption.api.ReceiverDTO;
import zarzyka.jagoda.shelter.adoption.model.ReceiverEntity;

public class ReceiverMapper {

    public ReceiverEntity fromDto(ReceiverDTO receiverDTO) {
        return ReceiverEntity.builder()
                .name(receiverDTO.getName())
                .surname(receiverDTO.getSurname())
                .address(new AddressMapper().fromDto(receiverDTO.getAddress()))
                .phoneNumber(receiverDTO.getPhoneNumber())
                .email(receiverDTO.getEmail())
                .build();
    }

    public ReceiverDTO toDto(ReceiverEntity receiverEntity) {
        return ReceiverDTO.builder()
                .name(receiverEntity.getName())
                .surname(receiverEntity.getSurname())
                .address(new AddressMapper().toDto(receiverEntity.getAddress()))
                .phoneNumber(receiverEntity.getPhoneNumber())
                .email(receiverEntity.getEmail())
                .build();
    }
}
