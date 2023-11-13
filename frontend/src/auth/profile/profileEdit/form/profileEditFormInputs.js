import { formValidators } from "../../../../validators/formValidators";
import moment from 'moment';

export const profileEditFormInputs = [
  {
    tag: "Image",
    name: "Image",
    type: "text",
    defaultValue: "",
    isRequired: true,
    validators: [formValidators.notEmptyValidator],
  },
  {
    tag: "Username",
    name: "username",
    type: "text",
    defaultValue: "",
    isRequired: true,
    validators: [formValidators.notEmptyValidator],
  },
];
