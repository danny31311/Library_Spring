package org.example.util;

import org.example.dao.PersonDAO;
import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class PersonValidator implements Validator {

    private final PersonDAO personDAO;
    @Autowired
    public PersonValidator(PersonDAO personDAO){
        this.personDAO = personDAO;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz );
    }
    //есть ли человек с таким же имененм в БД
    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if(personDAO.getPersonByName(person.getName()).isPresent()){
            errors.rejectValue("name", "","This name is already taken");
        }



    }
}
