import { useState } from "react";
import { Link } from "react-router-dom";
import {
  validateEmail,
  checkPasswordLength
} from "../Ultis/Validation";
import { getUserInfo, login} from "../apis/service";
import { jwtATom, userInfoAtom } from "../states/atom";
import { toast } from "react-toastify";
import { useCookies } from "react-cookie";
import { useRecoilState } from "recoil";
export default function SignIn() {
  const [email, setEmail] = useState("");
  const [password, setPass] = useState("");
  const [cookies, setCookie] = useCookies(["JWT"]);
  const [JWT, setJWT] = useRecoilState(jwtATom);
  const [userInfo, setUserInfo] = useRecoilState(userInfoAtom);
  const Login = async () => {
    if (!validateEmail(email)) {
      toast.warning("Email sai cú pháp");
      return;
    }

    if (!checkPasswordLength(password)) {
      toast.warning("Mật khẩu phải lớn hơn hoặc bằng 6 kí tự");
      return;
    }
    const result = await login({
      email: email,
      password: password,
    });
    if (result?.type != "error") {
      getUserInfo(result).then((res : any) => {
        if (res?.type == "error") {
          setCookie("JWT", undefined);
          setJWT(undefined);
          setUserInfo(undefined);
        } else {
          setCookie("JWT", result);
          setJWT(result);
          setUserInfo({ ...res, username: res.fullName });
          toast.info(`Xin chào ${res.fullName}`);
        }
      });
    }
  };
  return (
    <section className="bg-gray-50 dark:bg-gray-900">
      <div className="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-[calc(100vh-64px)] lg:py-0">
        <a
          href="#"
          className="flex items-center mb-6 text-2xl font-semibold text-gray-900 "
        >
          <img
            className="w-8 h-8 mr-2"
            src="https://flowbite.s3.amazonaws.com/blocks/marketing-ui/logo.svg"
            alt="logo"
          />
          Flowbite
        </a>
        <div className="w-full bg-white rounded-lg shadow border-2 border-gray-300 md:mt-0 sm:max-w-md xl:p-0 ">
          <div className="p-6 space-y-4 md:space-y-6 sm:p-8 ">
            <h1 className="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl ">
              Sign in to your account
            </h1>
            <form className="space-y-4 md:space-y-6" action="#">
              <div>
                <label
                  form="email"
                  className="block mb-2 text-sm font-medium text-gray-900 "
                >
                  Your email
                </label>
                <input
                  onChange={(event) => {
                    setEmail(event.target.value);
                  }}
                  type="email"
                  name="email"
                  id="email"
                  className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 "
                  placeholder="name@company.com"
                  required
                />
              </div>
              <div>
                <label
                  form="password"
                  className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                >
                  Password
                </label>
                <input
                  type="password"
                  name="password"
                  id="password"
                  onChange={(event) => {
                    setPass(event.target.value);
                  }}
                  placeholder="••••••••"
                  className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 "
                  required
                />
              </div>
              <div className="flex items-center justify-between">
                {/* <div className="flex items-start">
                  <div className="flex items-center h-5">
                    <input
                      id="remember"
                      aria-describedby="remember"
                      type="checkbox"
                      className="w-4 h-4 border border-gray-300 rounded bg-gray-50 focus:ring-3 focus:ring-primary-300 "
                      required
                    />
                  </div>
                  <div className="ml-3 text-sm">
                    <label
                      form="remember"
                      className="text-gray-500 dark:text-gray-300"
                    >
                      Remember me
                    </label>
                  </div>
                </div> */}
                <Link
                  to="/forgotPassword"
                  className="text-sm font-medium text-primary-600 hover:underline "
                >
                  Forgot password?
                </Link>
              </div>
              <button
                className="w-full text-white bg-blue-600 hover:bg-blue-700 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center "
                onClick={Login}
              >
                Sign in
              </button>
              <p className="text-sm font-light text-gray-500 ">
                Don’t have an account yet?{" "}
                <Link
                  to="/regis"
                  className="font-medium text-primary-600 hover:underline "
                >
                  Sign up
                </Link>
              </p>
            </form>
          </div>
        </div>
      </div>
    </section>
  );
}
