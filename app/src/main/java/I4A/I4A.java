package I4A;

/**
 * Created by acer on 10/5/2018.
 */


import android.util.Log;

import java.io.IOException;
import java.net.CookieStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import dev.niekirk.com.instagram4android.Instagram4Android;
import dev.niekirk.com.instagram4android.requests.InstagramAutoCompleteUserListRequest;
import dev.niekirk.com.instagram4android.requests.InstagramGetInboxRequest;
import dev.niekirk.com.instagram4android.requests.InstagramGetRecentActivityRequest;
import dev.niekirk.com.instagram4android.requests.InstagramLoginRequest;
import dev.niekirk.com.instagram4android.requests.InstagramRequest;
import dev.niekirk.com.instagram4android.requests.InstagramSyncFeaturesRequest;
import dev.niekirk.com.instagram4android.requests.internal.InstagramFetchHeadersRequest;
import dev.niekirk.com.instagram4android.requests.payload.InstagramLoginPayload;
import dev.niekirk.com.instagram4android.requests.payload.InstagramLoginResult;
import dev.niekirk.com.instagram4android.requests.payload.InstagramSyncFeaturesPayload;
import dev.niekirk.com.instagram4android.util.InstagramGenericUtil;
import dev.niekirk.com.instagram4android.util.InstagramHashUtil;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by acer on 9/20/2018.
 */

public class I4A extends Instagram4Android {
    private String TAG="I4ATAG";
    protected HashMap<String, Cookie> cookieStore = new HashMap<>();
    private String username;
    private String password;
    private String uuid;
    private String accessToken;
    private long userId;

    I4A(String username,String password){
        super(username,password);
        this.username=username;
        this.password=password;
    }
    public HashMap<String, Cookie> getCookieStore(){
        return this.cookieStore;
    }
    public void setCookieStore(HashMap<String,Cookie> cs){
        this.cookieStore=cs;
    }
    @Override
    public void setup() {
        this.deviceId = InstagramHashUtil.generateDeviceId(this.username, this.password);
        this.uuid = InstagramGenericUtil.generateUuid(true);
        this.client = (new OkHttpClient.Builder()).cookieJar(new CookieJar() {
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                if(cookies != null) {
                    Iterator var3 = cookies.iterator();

                    while(var3.hasNext()) {
                        Cookie cookie = (Cookie)var3.next();
                        cookieStore.put(cookie.name(), cookie);
                    }
                }

            }

            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> validCookies = new ArrayList();
                Iterator var3 = cookieStore.entrySet().iterator();

                while(var3.hasNext()) {
                    Map.Entry<String, Cookie> entry = (Map.Entry)var3.next();
                    Cookie cookie = (Cookie)entry.getValue();
                    if(cookie.expiresAt() >= System.currentTimeMillis()) {
                        validCookies.add(cookie);
                    }
                }

                return validCookies;
            }
        }).build();
    }
    public void reLogin() throws IOException {
//        InstagramLoginPayload loginRequest = InstagramLoginPayload.builder().
//                username(this.username).password(this.password).guid(this.uuid).
//                device_id(this.deviceId).phone_id(InstagramGenericUtil.generateUuid(true)).login_attempt_account(0)._csrftoken(this.getOrFetchCsrf((HttpUrl)null)).build();
        this.rankToken = this.userId + "_" + this.uuid;
        this.isLoggedIn = true;
        Log.d(TAG, "reLogin: "+this.rankToken);
//            InstagramSyncFeaturesPayload syncFeatures = InstagramSyncFeaturesPayload.builder()._uuid(this.uuid)._csrftoken(this.getOrFetchCsrf((HttpUrl)null))._uid(this.userId).id(this.userId).experiments("ig_android_progressive_jpeg,ig_creation_growth_holdout,ig_android_report_and_hide,ig_android_new_browser,ig_android_enable_share_to_whatsapp,ig_android_direct_drawing_in_quick_cam_universe,ig_android_huawei_app_badging,ig_android_universe_video_production,ig_android_asus_app_badging,ig_android_direct_plus_button,ig_android_ads_heatmap_overlay_universe,ig_android_http_stack_experiment_2016,ig_android_infinite_scrolling,ig_fbns_blocked,ig_android_white_out_universe,ig_android_full_people_card_in_user_list,ig_android_post_auto_retry_v7_21,ig_fbns_push,ig_android_feed_pill,ig_android_profile_link_iab,ig_explore_v3_us_holdout,ig_android_histogram_reporter,ig_android_anrwatchdog,ig_android_search_client_matching,ig_android_high_res_upload_2,ig_android_new_browser_pre_kitkat,ig_android_2fac,ig_android_grid_video_icon,ig_android_white_camera_universe,ig_android_disable_chroma_subsampling,ig_android_share_spinner,ig_android_explore_people_feed_icon,ig_explore_v3_android_universe,ig_android_media_favorites,ig_android_nux_holdout,ig_android_search_null_state,ig_android_react_native_notification_setting,ig_android_ads_indicator_change_universe,ig_android_video_loading_behavior,ig_android_black_camera_tab,liger_instagram_android_univ,ig_explore_v3_internal,ig_android_direct_emoji_picker,ig_android_prefetch_explore_delay_time,ig_android_business_insights_qe,ig_android_direct_media_size,ig_android_enable_client_share,ig_android_promoted_posts,ig_android_app_badging_holdout,ig_android_ads_cta_universe,ig_android_mini_inbox_2,ig_android_feed_reshare_button_nux,ig_android_boomerang_feed_attribution,ig_android_fbinvite_qe,ig_fbns_shared,ig_android_direct_full_width_media,ig_android_hscroll_profile_chaining,ig_android_feed_unit_footer,ig_android_media_tighten_space,ig_android_private_follow_request,ig_android_inline_gallery_backoff_hours_universe,ig_android_direct_thread_ui_rewrite,ig_android_rendering_controls,ig_android_ads_full_width_cta_universe,ig_video_max_duration_qe_preuniverse,ig_android_prefetch_explore_expire_time,ig_timestamp_public_test,ig_android_profile,ig_android_dv2_consistent_http_realtime_response,ig_android_enable_share_to_messenger,ig_explore_v3,ig_ranking_following,ig_android_pending_request_search_bar,ig_android_feed_ufi_redesign,ig_android_video_pause_logging_fix,ig_android_default_folder_to_camera,ig_android_video_stitching_7_23,ig_android_profanity_filter,ig_android_business_profile_qe,ig_android_search,ig_android_boomerang_entry,ig_android_inline_gallery_universe,ig_android_ads_overlay_design_universe,ig_android_options_app_invite,ig_android_view_count_decouple_likes_universe,ig_android_periodic_analytics_upload_v2,ig_android_feed_unit_hscroll_auto_advance,ig_peek_profile_photo_universe,ig_android_ads_holdout_universe,ig_android_prefetch_explore,ig_android_direct_bubble_icon,ig_video_use_sve_universe,ig_android_inline_gallery_no_backoff_on_launch_universe,ig_android_image_cache_multi_queue,ig_android_camera_nux,ig_android_immersive_viewer,ig_android_dense_feed_unit_cards,ig_android_sqlite_dev,ig_android_exoplayer,ig_android_add_to_last_post,ig_android_direct_public_threads,ig_android_prefetch_venue_in_composer,ig_android_bigger_share_button,ig_android_dv2_realtime_private_share,ig_android_non_square_first,ig_android_video_interleaved_v2,ig_android_follow_search_bar,ig_android_last_edits,ig_android_video_download_logging,ig_android_ads_loop_count_universe,ig_android_swipeable_filters_blacklist,ig_android_boomerang_layout_white_out_universe,ig_android_ads_carousel_multi_row_universe,ig_android_mentions_invite_v2,ig_android_direct_mention_qe,ig_android_following_follower_social_context").build();
//            this.sendRequest(new InstagramSyncFeaturesRequest(syncFeatures));
//            this.sendRequest(new InstagramAutoCompleteUserListRequest());
//            this.sendRequest(new InstagramGetInboxRequest());
//            this.sendRequest(new InstagramGetRecentActivityRequest());
    }
    public InstagramLoginResult login() throws IOException {
        InstagramLoginPayload loginRequest = InstagramLoginPayload.builder().username(this.username).password(this.password).guid(this.uuid).device_id(this.deviceId).phone_id(InstagramGenericUtil.generateUuid(true)).login_attempt_account(0)._csrftoken(this.getOrFetchCsrf((HttpUrl)null)).build();
        InstagramLoginResult loginResult = (InstagramLoginResult)this.sendRequest(new InstagramLoginRequest(loginRequest));
        if(loginResult.getStatus().equalsIgnoreCase("ok")) {
            this.userId = loginResult.getLogged_in_user().getPk();
            this.rankToken = this.userId + "_" + this.uuid;
            this.isLoggedIn = true;
            InstagramSyncFeaturesPayload syncFeatures = InstagramSyncFeaturesPayload.builder()._uuid(this.uuid)._csrftoken(this.getOrFetchCsrf((HttpUrl)null))._uid(this.userId).id(this.userId).experiments("ig_android_progressive_jpeg,ig_creation_growth_holdout,ig_android_report_and_hide,ig_android_new_browser,ig_android_enable_share_to_whatsapp,ig_android_direct_drawing_in_quick_cam_universe,ig_android_huawei_app_badging,ig_android_universe_video_production,ig_android_asus_app_badging,ig_android_direct_plus_button,ig_android_ads_heatmap_overlay_universe,ig_android_http_stack_experiment_2016,ig_android_infinite_scrolling,ig_fbns_blocked,ig_android_white_out_universe,ig_android_full_people_card_in_user_list,ig_android_post_auto_retry_v7_21,ig_fbns_push,ig_android_feed_pill,ig_android_profile_link_iab,ig_explore_v3_us_holdout,ig_android_histogram_reporter,ig_android_anrwatchdog,ig_android_search_client_matching,ig_android_high_res_upload_2,ig_android_new_browser_pre_kitkat,ig_android_2fac,ig_android_grid_video_icon,ig_android_white_camera_universe,ig_android_disable_chroma_subsampling,ig_android_share_spinner,ig_android_explore_people_feed_icon,ig_explore_v3_android_universe,ig_android_media_favorites,ig_android_nux_holdout,ig_android_search_null_state,ig_android_react_native_notification_setting,ig_android_ads_indicator_change_universe,ig_android_video_loading_behavior,ig_android_black_camera_tab,liger_instagram_android_univ,ig_explore_v3_internal,ig_android_direct_emoji_picker,ig_android_prefetch_explore_delay_time,ig_android_business_insights_qe,ig_android_direct_media_size,ig_android_enable_client_share,ig_android_promoted_posts,ig_android_app_badging_holdout,ig_android_ads_cta_universe,ig_android_mini_inbox_2,ig_android_feed_reshare_button_nux,ig_android_boomerang_feed_attribution,ig_android_fbinvite_qe,ig_fbns_shared,ig_android_direct_full_width_media,ig_android_hscroll_profile_chaining,ig_android_feed_unit_footer,ig_android_media_tighten_space,ig_android_private_follow_request,ig_android_inline_gallery_backoff_hours_universe,ig_android_direct_thread_ui_rewrite,ig_android_rendering_controls,ig_android_ads_full_width_cta_universe,ig_video_max_duration_qe_preuniverse,ig_android_prefetch_explore_expire_time,ig_timestamp_public_test,ig_android_profile,ig_android_dv2_consistent_http_realtime_response,ig_android_enable_share_to_messenger,ig_explore_v3,ig_ranking_following,ig_android_pending_request_search_bar,ig_android_feed_ufi_redesign,ig_android_video_pause_logging_fix,ig_android_default_folder_to_camera,ig_android_video_stitching_7_23,ig_android_profanity_filter,ig_android_business_profile_qe,ig_android_search,ig_android_boomerang_entry,ig_android_inline_gallery_universe,ig_android_ads_overlay_design_universe,ig_android_options_app_invite,ig_android_view_count_decouple_likes_universe,ig_android_periodic_analytics_upload_v2,ig_android_feed_unit_hscroll_auto_advance,ig_peek_profile_photo_universe,ig_android_ads_holdout_universe,ig_android_prefetch_explore,ig_android_direct_bubble_icon,ig_video_use_sve_universe,ig_android_inline_gallery_no_backoff_on_launch_universe,ig_android_image_cache_multi_queue,ig_android_camera_nux,ig_android_immersive_viewer,ig_android_dense_feed_unit_cards,ig_android_sqlite_dev,ig_android_exoplayer,ig_android_add_to_last_post,ig_android_direct_public_threads,ig_android_prefetch_venue_in_composer,ig_android_bigger_share_button,ig_android_dv2_realtime_private_share,ig_android_non_square_first,ig_android_video_interleaved_v2,ig_android_follow_search_bar,ig_android_last_edits,ig_android_video_download_logging,ig_android_ads_loop_count_universe,ig_android_swipeable_filters_blacklist,ig_android_boomerang_layout_white_out_universe,ig_android_ads_carousel_multi_row_universe,ig_android_mentions_invite_v2,ig_android_direct_mention_qe,ig_android_following_follower_social_context").build();
            this.sendRequest(new InstagramSyncFeaturesRequest(syncFeatures));
            this.sendRequest(new InstagramAutoCompleteUserListRequest());
            this.sendRequest(new InstagramGetInboxRequest());
            this.sendRequest(new InstagramGetRecentActivityRequest());
        }

        return loginResult;
    }

    public String getOrFetchCsrf(HttpUrl url) throws IOException {
        Cookie cookie = this.getCsrfCookie(url);
        if(cookie == null) {
            this.sendRequest(new InstagramFetchHeadersRequest());
            cookie = this.getCsrfCookie(url);
        }

        return cookie.value();
    }

    public Cookie getCsrfCookie(HttpUrl url) {
        Iterator var2 = this.client.cookieJar().loadForRequest(url).iterator();

        Cookie cookie;
        do {
            if(!var2.hasNext()) {
                return null;
            }

            cookie = (Cookie)var2.next();
        } while(!cookie.name().equalsIgnoreCase("csrftoken"));

        return cookie;
    }

    public <T> T sendRequest(InstagramRequest<T> request) throws IOException {
        if(!this.isLoggedIn && request.requiresLogin()) {
            throw new IllegalStateException("Need to login first!");
        } else {
            request.setApi(this);
            T response = request.execute();
            return response;
        }
    }

//    public static I4A.MyInstagram4AndroidBuilder builder() {
//        return new I4A.MyInstagram4AndroidBuilder();
//    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public boolean isLoggedIn() {
        return this.isLoggedIn;
    }

    public String getUuid() {
        return this.uuid;
    }

    public String getRankToken() {
        return this.rankToken;
    }

    public void setRankToken(String rankToken) {
        this.rankToken = rankToken;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
    public String getUUId() {
        return this.uuid;
    }

    public void setUUId(String userId) {
        this.uuid = userId;
    }

    public Response getLastResponse() {
        return this.lastResponse;
    }

    public void setLastResponse(Response lastResponse) {
        this.lastResponse = lastResponse;
    }

    public OkHttpClient getClient() {
        return this.client;
    }

    public static class MyInstagram4AndroidBuilder {
        private String username;
        private String password;

        MyInstagram4AndroidBuilder() {
        }

        public I4A.MyInstagram4AndroidBuilder username(String username) {
            this.username = username;
            return this;
        }

        public I4A.MyInstagram4AndroidBuilder password(String password) {
            this.password = password;
            return this;
        }
        public I4A.MyInstagram4AndroidBuilder cookieStore(HashMap<String, Cookie> cookieStore ) {
            cookieStore = cookieStore;
            return this;
        }

        public I4A build() {
            return new I4A(this.username, this.password);
        }

        public String toString() {
            return "Instagram4Android.Instagram4AndroidBuilder(username=" + this.username + ", password=" + this.password + ")";
        }
    }
}

