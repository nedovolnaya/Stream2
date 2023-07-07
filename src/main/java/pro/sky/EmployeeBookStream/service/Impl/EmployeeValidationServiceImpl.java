package pro.sky.EmployeeBookStream.service.Impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pro.sky.EmployeeBookStream.exception.InvalidEmployeeDataException;
import pro.sky.EmployeeBookStream.service.EmployeeValidationService;

@Service

public class EmployeeValidationServiceImpl implements EmployeeValidationService {
    @Override
    public void validate(String firstName, String lastName) {
        validateName(firstName);
        validateName(lastName);
    }

    private void validateName(String name) {
        if (!StringUtils.isAlpha(name)) {
            throw new InvalidEmployeeDataException();

        }
    }
}
