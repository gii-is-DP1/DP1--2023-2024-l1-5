import React from 'react'
import "../static/css/main.css"
import "../static/css/player/gameRules.css"

export default function GameRules() {

  const imagePaths = [
    'https://i.imgur.com/P5d3qbK.jpeg', // 1
    'https://i.imgur.com/poq7e8a.jpeg', // 3
    'https://i.imgur.com/SPyjIzq.jpeg', // 5
    'https://i.imgur.com/dnbylbJ.jpeg', // 7
    'https://i.imgur.com/xg0Ip2C.jpeg', // 2
    'https://i.imgur.com/doqI44d.jpeg', // 4
    'https://i.imgur.com/FM0lzzV.jpeg', // 6
    'https://i.imgur.com/ENbj5D3.jpeg', // 8
  ];
  return (
    <div class="wallpaper">
      <div class="image-view">
        <div class="image-row">
          {imagePaths.slice(0, 4).map((path, index) => (
            <div key={index}>
              <img class="image" src={path} alt={`Imagen ${index + 1}`} />
            </div>
          ))}
        </div>
        <div class="image-row">
          {imagePaths.slice(4).map((path, index) => (
            <div key={index + 4}>
              <img class="image" src={path} alt={`Imagen ${index + 5}`} />
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};