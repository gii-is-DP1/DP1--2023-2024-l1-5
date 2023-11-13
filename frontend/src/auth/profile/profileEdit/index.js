import React from "react";
import { useEffect, useState, useRef } from "react";
import { Link } from "react-router-dom";
import getErrorModal from "../../../util/getErrorModal";
import { profileEditFormInputs } from "./form/profileEditFormInputs";
import FormGenerator from "../../../components/formGenerator/formGenerator";
import tokenService from "../../../services/token.service";
import useFetchState from "../../../util/useFetchState";

const persona = tokenService.getUser();

export default function ProfileEdit(){

    const emptyUser = {
      id: null,
      username: "",
      authority:{}
    };

    const emptyPlayer = {
        id: null,
        firstname: "",
        lastname: "",
        image: "",
        state:null,
        user:{},
        friendList:{}
    }; 
    
    const editProfileFormRef=useRef();
    const jwt = JSON.parse(window.localStorage.getItem("jwt"));
    const [message,setMessage] = useState(null);
    const [visible, setVisible] = useState(false);
    const [player,setPlayer] = useState(emptyPlayer);  
    const [user,setUser] = useState(emptyUser);  

    async function setUp() {
      console.log("set up llamado")
      const playersFetch = await (
        await fetch(`/api/v1/players`, {
          headers: {
            Authorization: `Bearer ${jwt}`,
            "Content-Type": "application/json",
          },
        })
      ).json();
      const onePlayer  = playersFetch.filter((x)=> x.user.id === persona.id)
      setPlayer(onePlayer);

      const usersFetch = await (
        await fetch(`/api/v1/users`, {
          headers: {
            Authorization: `Bearer ${jwt}`,
            "Content-Type": "application/json",
          },
        })
      ).json();
      const oneUser  = usersFetch.filter((x)=> x.id === persona.id)
      setUser(oneUser);
    }

    useEffect(() => {
      setUp();
    }, []);

      function handleSubmit({ values }) {

        if (!editProfileFormRef.current.validate()) return;

        const editUser = {
          id: user.id,
          username: values["username"],
          authority: user.authority
        }

        const editPlayer = {
          id: player.id,
          firstname: values["firstName"],
          lastname: values["lastName"],
          image: values["image"],
          state: player.state,
          user: editUser,
          friendList: player.friendList
        };


        fetch("/api/v1/players" + (editPlayer.id ? "/" + editPlayer.id : ""), 
          {
            method: editPlayer.id ? "PUT" : "POST",
            headers: {
              Authorization: `Bearer ${jwt}`,
              Accept: "application/json",
              "Content-Type": "application/json",
            },
            body: JSON.stringify(editPlayer),
          }).then((response) => response.json())
          .then((json) => {
            if (json.message) {
              setMessage(json.message);
              setVisible(true);
            } else window.location.href = "/profile";
          })
          .catch((message) => alert(message));
          console.log(editPlayer)
      }

    const modal = getErrorModal(setVisible, visible, message);

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
            <div className="button-container-back">
                <Link className="auth-button" to="/profile" style={{textDecoration: "none"}}>Back</Link>
            </div>
          </div>
          {modal}
        </div>
      );

}
