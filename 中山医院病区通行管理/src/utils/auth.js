import Cookies from "js-cookie"


export function getToken () {
  return Cookies.get("Admin-Token")
}

export function setToken (token) {
  return Cookies.set("Admin-Token", token)
}

export function removeToken () {
  return Cookies.remove("Admin-Token")
}

export function setWardsName (wardsName) {
  return Cookies.set("WardsName", wardsName)
}

export function getWardsName () {
  return Cookies.get("WardsName");
}

export function setUserName (userName) {
  return Cookies.set("UserName", userName)
}

export function getUserName () {
  return Cookies.get("UserName");
}

export function setWardsId (wardsId) {
  return Cookies.set("WardsId", wardsId)
}

export function getWardsId () {
  return Cookies.get("WardsId");
}

export function setRole (role) {
  return Cookies.set("Role", role)
}

export function getRole () {
  return Cookies.get("Role");
}
