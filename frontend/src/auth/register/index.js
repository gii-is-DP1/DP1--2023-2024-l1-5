import "../../static/css/auth/authButton.css";
import "../../static/css/auth/authPage.css";
import tokenService from "../../services/token.service";
import FormGenerator from "../../components/formGenerator/formGenerator";
import { registerFormOwnerInputs } from "./form/registerFormOwnerInputs";
import { registerFormVetInputs } from "./form/registerFormVetInputs";
import { registerFormClinicOwnerInputs } from "./form/registerFormClinicOwnerInputs";
import { registerFormPlayerInputs } from "./form/registerFormPlayerInputs";
import { useEffect, useRef, useState } from "react";

export default function Register() {
  let [type, setType] = useState(null);
  let [authority, setAuthority] = useState(null);
  let [clinics, setClinics] = useState([]);

  const registerFormRef = useRef();

  function handleButtonClick(event) {
    const target = event.target;
    let value = target.value;
    if (value === "Back") value = null;
    else setAuthority(value);
    setType(value);
  }

  function handleSubmit({ values }) {
    if (!registerFormRef.current.validate()) return;
  
    const request = values;
    request.clinic = clinics.find((clinic) => clinic.name === request.clinic);
    request.authority = authority;
  
    fetch("/api/v1/auth/signup", {
      headers: { "Content-Type": "application/json" },
      method: "POST",
      body: JSON.stringify(request),
    })
      .then(async (response) => {
        if (response.ok) {
          // Signup successful, proceed with login
          const loginRequest = {
            username: request.username,
            password: request.password,
          };
  
          const loginResponse = await fetch("/api/v1/auth/signin", {
            headers: { "Content-Type": "application/json" },
            method: "POST",
            body: JSON.stringify(loginRequest),
          });
  
          if (loginResponse.ok) {
            // Login successful
            const data = await loginResponse.json();
            tokenService.setUser(data);
            tokenService.updateLocalAccessToken(data.token);
            window.location.href = "/dashboard";
          } else {
            // Login failed
            const loginErrorData = await loginResponse.json();
            alert(loginErrorData.message);
          }
        } else {
          // Signup failed
          const signupErrorData = await response.json();
          alert(signupErrorData.message);
        }
      })
      .catch((error) => {
        alert(error.message || "An error occurred during signup");
      });
  }
  

  useEffect(() => {
    if (type === "Owner" || type === "Vet") {
      if (registerFormOwnerInputs[5].values.length === 1){
        fetch("/api/v1/clinics")
        .then(function (response) {
          if (response.status === 200) {
            return response.json();
          } else {
            return response.json();
          }
        })
        .then(function (data) {
          setClinics(data);
          if (data.length !== 0) {
            let clinicNames = data.map((clinic) => {
              return clinic.name;
            });

            registerFormOwnerInputs[5].values = ["None", ...clinicNames];
          }
        })
        .catch((message) => {
          alert(message);
        });
      }
    }
  }, [type]);

  if (type) {
    return (
      <div className="auth-page-container">
        <h1>Register</h1>
        <div className="auth-form-container">
          <FormGenerator
            ref={registerFormRef}
            inputs={
              type === "Owner" ? registerFormOwnerInputs 
              : type === "Vet" ? registerFormVetInputs
              : type === "Player" ? registerFormPlayerInputs
              : registerFormClinicOwnerInputs
            }
            onSubmit={handleSubmit}
            numberOfColumns={1}
            listenEnterKey
            buttonText="Save"
            buttonClassName="auth-button"
          />
        </div>
      </div>
    );
  } else {
    return (
      <div className="auth-page-container">
        <div className="auth-form-container">
          <h1>Register</h1>
          <h2 className="text-center text-md">
            What type of user will you be?
          </h2>
          <div className="options-row">
            <button
              className="auth-button"
              value="Owner"
              onClick={handleButtonClick}
            >
              Owner
            </button>
            <button
              className="auth-button"
              value="Vet"
              onClick={handleButtonClick}
            >
              Vet
            </button>
            <button
              className="auth-button"
              value="Clinic Owner"
              onClick={handleButtonClick}
            >
              Clinic Owner
            </button>
            <button
              className="auth-button"
              value="Player"
              onClick={handleButtonClick}
            >
              Player
            </button>
          </div>
        </div>
      </div>
    );
  }
}
