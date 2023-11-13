import React from "react";
import { useEffect, useState, useRef } from "react";
import { Link } from "react-router-dom";
import {
  Button,
  Modal,
  ModalBody,
  ModalFooter,
  ModalHeader,
} from "reactstrap";
import { profileEditFormInputs } from "./form/profileEditFormInputs";
import FormGenerator from "../../../components/formGenerator/formGenerator";
import tokenService from "../../../services/token.service";

const user = tokenService.getUser();

export default function ProfileEdit(){

    let pathArray = window.location.pathname.split("/");
    const emptyItem = {
        id: null,
        firstname: "",
        lastname: "",
        username: "",
        image: "",
        state:null,
        user:{},
        friendList:{}
    }; 
    
    const editProfileFormRef=useRef();
    let rol = String(user.roles).toLowerCase()+'s';
    const jwt = JSON.parse(window.localStorage.getItem("jwt"));
    const [message,setMessage] = useState(null);
    const [modalShow,setModalShow] = useState(false);
    const [player,setPlayer] = useState(emptyItem);  

    useEffect( () => setUpUser(),[]);  

    function setUpUser() {
        const player =  (
            fetch(`/api/v1/${rol}`, {
            headers: {
              Authorization: `Bearer ${jwt}`,
              "Content-Type": "application/json",
            },
          })
          .then((p) => p.json())
          .then((p) => {
            if(p.message){ 
              setMessage(player.message);
              setModalShow( true );
            }else {
            const usuario = p.filter((x)=> x.user.id === user.id)
            setPlayer(usuario);
            }
          }).catch(m =>{
            setMessage(m);
            setModalShow( true );
          }) 
        )
        console.log(player)
      }

      async function handleSubmit({ values }) {

        if (!editProfileFormRef.current.validate()) return;
        const actPlayer = {
          id: player.id,
          firstname: player.firstname,
          lastname: player.lastname,
          username: values["username"],
          image: values["image"],
          state: player.state,
          user: player.user,
          friendList: player.friendList
        };
        const submit = await (await fetch("/api/v1/pets" + (actPlayer.id ? "/" + actPlayer : ""), 
          {
            method: actPlayer.id ? "PUT" : "POST",
            headers: {
              Authorization: `Bearer ${jwt}`,
              Accept: "application/json",
              "Content-Type": "application/json",
            },
            body: JSON.stringify(actPlayer),
          }
        )).json();
        if (submit.message){
          setMessage(submit.message);
          setModalShow(true);
        }
        else window.location.href = `/profile`;
      }

    function handleShow() {
        setModalShow(false);
        setMessage(null);
      }

    let modal = <></>;
    if (message) {
      const show = modalShow;
      const closeBtn = (
        <button className="close" onClick={handleShow} type="button">
          &times;
        </button>
      );
      const cond = message.includes("limit");
      modal = (
        <div>
          <Modal isOpen={show} toggle={handleShow} keyboard={false}>
            {cond ? (
              <ModalHeader>Warning!</ModalHeader>
            ) : (
              <ModalHeader toggle={handleShow} close={closeBtn}>
                Error!
              </ModalHeader>
            )}
            <ModalBody>{message || ""}</ModalBody>
            <ModalFooter>
              <Button color="primary" tag={Link} to={`/myPets`}>
                Back
              </Button>
            </ModalFooter>
          </Modal>
        </div>
      );
    }

    return (
        <div className="edit-pet-page-container">
          <div className="edit-pet-form-container">
            <h2 className="text-center">Edit Profile</h2>
                <FormGenerator
                ref={editProfileFormRef}
                inputs={profileEditFormInputs}
                onSubmit={handleSubmit}
                buttonText="Save"
                buttonClassName="auth-button"
                />
          </div>
          {modal}
            <div className="button-container-back">
                  <Link className="auth-button" to="/profile" style={{textDecoration: "none"}}>Back</Link>
            </div>
        </div>
      );

}
