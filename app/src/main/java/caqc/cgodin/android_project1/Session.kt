package caqc.cgodin.android_project1

//A logged session data
class Session {
    companion object{
        var current_session: Session? = null
            get() {
                //TODO: if current session == null : return to log in
                return field
            }
            set(value) {
                //TODO: init the new session
                field = value
            }

    }
}