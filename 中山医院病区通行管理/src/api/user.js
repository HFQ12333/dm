import request from '@/utils/request'

export function login (data) {
  return request({
    url: '/logins/account_login',
    method: 'post',
    data
  })
}

export function getInfo (token) {
  return request({
    url: '/logins/introspect',
    method: 'post',
    headers: { 'Authorization': token }
  })
}

export function logout (token) {
  return request({
    url: '/logins/logout',
    method: 'post',
    headers: { 'Authorization': token }
  })
}
