package com.joker.thanglong.Mention.utils;

import android.content.Context;

import com.joker.thanglong.Mention.models.User;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Converts all the user data in the file users.json to an array of {@link User} objects.
 */
public class MentionsLoaderUtils {

    private final Context context;
    private final List<User> userList;

    public MentionsLoaderUtils(final Context context,List <User> userList) {
        this.context = context;
        this.userList = userList;
    }

    /**
     * Loads users from JSON file.
     */
//    private List<User> loadUsers() {
//        final Gson gson = new Gson();
//        List<User> users = new ArrayList<>();
//
//        try {
//            final InputStream fileReader = context.getResources().openRawResource(R.raw.users);
//            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileReader, "UTF-8"));
//            users = gson.fromJson(bufferedReader, new TypeToken<List<User>>(){}.getType());
//        } catch (IOException ex) {
//            Log.e("Mentions Sample", "Error: Failed to parse json file.");
//        }
//
//        return users;
//    }

    /**
     * Search for user with name matching {@code query}.
     *
     * @return a list of users that matched {@code query}.
     */
    public List<User> searchUsers(String query) {
        final List<User> searchResults = new ArrayList<>();
        if (StringUtils.isNotBlank(query)) {
            query = query.toLowerCase(Locale.US);
            if (userList != null && !userList.isEmpty()) {
                for (User user : userList) {
                        searchResults.add(user);
                }
            }

        }
        return searchResults;
    }

}
