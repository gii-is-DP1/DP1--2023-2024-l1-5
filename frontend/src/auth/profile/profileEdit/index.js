import React from "react";
import { useEffect, useState} from "react";
import { Link } from "react-router-dom";
import getErrorModal from "../../../util/getErrorModal";
import { profileEditFormInputs } from "./form/profileEditFormInputs";
import FormGenerator from "../../../components/formGenerator/formGenerator";
import tokenService from "../../../services/token.service";
import "../../../static/css/main.css";

const persona = tokenService.getUser();
const jwt = JSON.parse(window.localStorage.getItem("jwt"));

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
    
    const [message,setMessage] = useState(null);
    const [visible, setVisible] = useState(false);
    const [player, setPlayer] = useState(emptyPlayer);  
    const [user, setUser] = useState(emptyUser);


    async function setUpPlayerUser() {
      const playersFetch = await (
        await fetch(`/api/v1/players`, {
          headers: {
            Authorization: `Bearer ${jwt}`,
            "Content-Type": "application/json",
          },
        })
      ).json();
      const onePlayer  = playersFetch.filter((x)=> x.user.id === persona.id)[0]
      setPlayer(onePlayer);
      console.log(player);

      const usersFetch = await (
        await fetch(`/api/v1/users`, {
          headers: {
            Authorization: `Bearer ${jwt}`,
            "Content-Type": "application/json",
          },
        })
      ).json();
      const oneUser  = usersFetch.filter((x)=> x.id === persona.id)[0];
      setUser(oneUser);
      console.log(user);
    }

    useEffect(() => {
      setUpPlayerUser()
    }, []);

    useEffect(() => {}, [user, player]);


      function handleSubmit({ values }) {

        const editUser = {
          id: user.id,
          username: user.username,
          authority: user.authority
        }

        const editPlayer = {
          id: player.id,
          firstName: values["firstName"],
          lastName: values["lastName"],
          image: values["image"],
          state: player.state,
          user: editUser,
          friendsList: player.friendsList
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
      }

    const modal = getErrorModal(setVisible, visible, message);

    return (
        <div className="wallpaper">
          <div className="section">
            <h1 className="text-center">Edit Profile</h1>
                <FormGenerator
                inputs={profileEditFormInputs}
                onSubmit={handleSubmit}
                buttonText="Save"
                buttonClassName="purple-button"
                />
          </div>
          {modal}
        </div>
      );

}
