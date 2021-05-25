(ns clojure-image-load.core
  (:require [clj-curl.easy :as curl-easy]
            [clj-curl.opts :as opts])
  (:import [clj_curl.Handlers FileHandler]))

(def +universe-image-url+ 
  "https://images.universe.com/ace26761-7e6f-4271-a6ba-73c27f3169f8/-/quality/lightest/-/format/jpeg/")

(def +universe-local-filepath+ "./universe.jpg")

(defn url->file! 
  [url filepath]
  ;FileHandler needs to be closed after you finished to use it.
  ;creating it with 'with-open' does that automatically for you.
  (with-open [filehldr (FileHandler. filepath)]
    (let [curl (curl-easy/init)]
      (do
        (println "simple http")
        (curl-easy/setopt curl opts/url url)
        (curl-easy/setopt curl opts/writefunction filehldr)
        (curl-easy/perform curl)
        (println "speed download: " (curl-easy/getinfo-double curl opts/speed-download))
        (curl-easy/cleanup curl)))))
;;
;; Sample for using CURL library wrapper for clojure.
;; Suitable to substitute error cases with
;; clojure.javaio./input-stream 
;;
(defn -main []
  (url->file! +universe-image-url+ 
              +universe-local-filepath+))

;; Commented code below produces error 451
#_(clojure.java.io/input-stream +universe-image-url+)
