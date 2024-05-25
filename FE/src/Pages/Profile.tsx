import { useRecoilState } from "recoil";
import { jwtATom, userInfoAtom } from "../states/atom";
import { getUserInfo,updateProfile } from "../apis/service";
import { useCookies } from "react-cookie";
import { useNavigate } from "react-router-dom";
import { useEffect } from "react";
import {UpdateUserInfo } from "../apis/apis";
export default function Profile() {
  const [userInfo, setUserInfo] = useRecoilState(userInfoAtom);
  const [JWT, setJWT] = useRecoilState(jwtATom);
  const [cookies, setCookie, removeCookie] = useCookies(["JWT"]);
  const navigate = useNavigate();
  useEffect(() => {
    getUserInfo(JWT).then((res : any) => {
      if (res?.type == "error") {
        setJWT(undefined);
        setUserInfo(undefined);
        navigate("/login");
      } else {
        setUserInfo({ ...res});
      }
    });
  }, []);
  const updateUserInfo = async () => {
    updateProfile(JWT, UpdateUserInfo, {
      phone: userInfo.phone,
      address: userInfo.address,
      password: userInfo.password,
    });
  };
  return (
    <div className=" sm:mx-32 lg:mx-32 xl:mx-72 h-[calc(100vh-64px)] ">
      <div className="flex justify-between container mx-auto">
        <div className="w-full">
          <div className="mt-4 px-4">
            <h1 className="font-thinner flex text-4xl pt-10 px-5">
              Your Profile
            </h1>

            <form className="mx-5 my-5">
              <label className="relative block p-3 border-2 border-black rounded">
                <span className="text-md font-semibold text-zinc-900">
                  UserName
                </span>
                <input
                  className="w-full bg-transparent p-0 text-sm  text-gray-500 focus:outline-none"
                  id="name"
                  value={userInfo?.username}
                  onChange={(event) =>
                    setUserInfo({ ...userInfo, phone: event.target.value })
                  }
                  type="text"
                  placeholder="UserName"
                />
              </label>

              <label className="relative block p-3 border-2 border-black rounded">
                <span className="text-md font-semibold text-zinc-900">
                  Full Name
                </span>
                <input
                  value={userInfo?.fullName}
                  onChange={(event) =>
                    setUserInfo({ ...userInfo, fullName: event.target.value })
                  }
                  className="w-full bg-transparent p-0 text-sm  text-gray-500 focus:outline-none"
                  id="name"
                  type="text"
                  placeholder="Full Name"
                />
              </label>


              <div className="shrink-0 mt-5">
                <img
                  className="h-20 w-20 object-cover rounded-full"
                  src="https://sahilnetic.xyz/sahilnetic.png"
                  alt="Current profile photo"
                />
              </div>
              <label className="block pt-2">
                <span className="sr-only t-2">Choose profile photo</span>
                <input
                  type="file"
                  className="w-full text-sm text-slate-500
          file:mr-4 file:py-2 file:px-4
          file:rounded-full file:border-0
          file:text-sm file:font-semibold
          file:bg-pink-300 file:text-zinc-900
          hover:file:bg-rose-300
        "
                />
              </label>

              <div className="flex justify-between items-center">
                <label className="relative block p-3 border-2 mt-5 border-black rounded w-full">
                  <span className="text-md font-semibold text-zinc-900">
                    Email
                  </span>

                  <input
                    value={userInfo?.email}
                    onChange={(event) =>
                      setUserInfo({ ...userInfo, email: event.target.value })
                    }
                    className="w-full   p-0 text-sm border-none bg-transparent text-gray-500 focus:outline-none"
                    id="Email"
                    type="email"
                    placeholder="Your email"
                  />
                </label>
              </div>

              <div className="flex justify-between items-center">
                <label className="relative block p-3 border-2 mt-5 border-black rounded w-1/2 mr-2">
                  <span className="text-md font-semibold text-zinc-900">
                    PhoneNumber
                  </span>

                  <input
                    value={userInfo?.phone}
                    onChange={(event) =>
                      setUserInfo({ ...userInfo, phone: event.target.value })
                    }
                    className="w-full   p-0 text-sm border-none bg-transparent text-gray-500 focus:outline-none"
                    id="phoneNumber"
                    type="text"
                    placeholder="Phone Number"
                  />
                </label>
              </div>

              <label className="relative block p-3 mt-5 border-2 border-black rounded">
                <span className="text-md font-semibold text-zinc-900">
                  Address
                </span>
                <input
                  value={userInfo?.address}
                  onChange={(event) =>
                    setUserInfo({ ...userInfo, address: event.target.value })
                  }
                  className="w-full bg-transparent p-0 text-sm  text-gray-500 focus:outline-none"
                  id="address"
                  type="text"
                  placeholder="Your address"
                />
              </label>

              <button className="mt-5 border-2 px-5 py-2 rounded-lg border-black border-b-4 font-black translate-y-2 border-l-4" 
              onClick={updateUserInfo}
              >
                Submit
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}
