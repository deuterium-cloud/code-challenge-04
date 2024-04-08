import React, { useContext } from 'react'
import { createRoot } from 'react-dom/client'
// @ts-ignore
import { AuthContext, AuthProvider, TAuthConfig, IAuthContext } from 'react-oauth2-code-pkce'
 
const authConfig: TAuthConfig = {
  clientId: process.env.REACT_APP_CLIENT_ID || '',
  authorizationEndpoint: process.env.REACT_APP_AUTHORIZATION_ENDPOINT || '',
  logoutEndpoint: process.env.REACT_APP_LOGOUT_ENDPOINT || '',
  tokenEndpoint: process.env.REACT_APP_TOKEN_ENDPOINT || '',
  redirectUri: process.env.REACT_APP_REDIRECT_URI || '',
  scope: 'openid',
  decodeToken: true,
  autoLogin: false,
}

function LoginInfo(): JSX.Element {
  const { tokenData, token, login, logOut, idToken, idTokenData, error, ...rest}: IAuthContext = useContext(AuthContext)

  console.log(rest)

  if (error) {
    return (
      <>
        <div style={{ color: 'red' }}>An error occurred during authentication: {error}</div>
        <button onClick={() => logOut()}>Logout</button>
      </>
    )
  }

  return (
    <>
      {token ? (
        <>
          <div>
            <h4>Access Token (JWT)</h4>
            <pre
              style={{
                width: '400px',
                margin: '10px',
                padding: '5px',
                border: 'black 2px solid',
                wordBreak: 'break-all',
                whiteSpace: 'break-spaces',
              }}
            >
              {token}
            </pre>
          </div>
          <div>
            <h4>Login Information from Access Token (Base64 decoded JWT)</h4>
            <pre
              style={{
                width: '400px',
                margin: '10px',
                padding: '5px',
                border: 'black 2px solid',
                wordBreak: 'break-all',
                whiteSpace: 'break-spaces',
              }}
            >
              {JSON.stringify(tokenData, null, 2)}
            </pre>
          </div>

          <div>
            <h4>Id Token (JWT)</h4>
            <pre
              style={{
                width: '400px',
                margin: '10px',
                padding: '5px',
                border: 'black 2px solid',
                wordBreak: 'break-all',
                whiteSpace: 'break-spaces',
              }}
            >
              {idToken}
            </pre>
          </div>
          <div>
            <h4>Login Information from Id Token (Base64 decoded JWT)</h4>
            <pre
              style={{
                width: '400px',
                margin: '10px',
                padding: '5px',
                border: 'black 2px solid',
                wordBreak: 'break-all',
                whiteSpace: 'break-spaces',
              }}
            >
              {JSON.stringify(idTokenData, null, 2)}
            </pre>
          </div>
          <button onClick={() => logOut()}>Logout</button>
        </>
      ) : (
        <>
          <div>You are not logged in.</div>
          <button onClick={() => login()}>Login</button>
        </>
      )}
    </>
  )
}

const container = document.getElementById('root')
const root = createRoot(container)

root.render(
  <div>
    <div>
      <h1>MaxBet World of Gamecraft coding challenge</h1>
    </div>
    <AuthProvider authConfig={authConfig}>
      {/* @ts-ignore*/}
      <LoginInfo />
    </AuthProvider>
  </div>,
  document.getElementById('root')
)