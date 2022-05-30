import {LatLng} from "leaflet";

export type Location = {
  latitude: number;
  longitude: number;
  sreetName?: string;
  houseNumber?: number;
  town?: string;
  postalCode?:number;
}

export const getDistance = (location1 : Location, location2 : Location) => {
  return (new LatLng(location1.latitude, location1.longitude)).distanceTo((new LatLng(location2.latitude, location2.longitude)));
}


