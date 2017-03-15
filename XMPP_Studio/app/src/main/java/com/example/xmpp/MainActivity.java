package com.example.xmpp;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import org.apache.harmony.javax.security.sasl.SaslException;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketExtensionFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smackx.disco.ServiceDiscoveryManager;
import org.jivesoftware.smackx.filetransfer.FileTransferNegotiator;

import com.example.xmpp.dao.PositionItem;
import com.example.xmpp.iq.IQCreatePosition;
import com.example.xmpp.iq.IQGetPositionALL;
import com.example.xmpp.iq.IQGetPositionCommon;
import com.example.xmpp.iq.IQRemovePosition;
import com.example.xmpp.iq.IQUpdatePosition;
import com.example.xmpp.xml.GetGroupCommonExtension;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Saveen Dhiman on 02-03-17
 * Main activity use for user login register and getting messages and packets
 */


public class MainActivity extends Activity implements OnClickListener {


	private UserLoginTask mAuthTask = null;
	String user_name1 = "android";

	TextView textView1;
	private Button button1, btn_ValuesAdd, btn_ValuesGet, btn_ValuesGetAll, btn_ValuesUpdate, btn_ValuesDelete,
			btnprintlist;
	ChatManagerListener chatListener;
	MessageListener messageListener;
	PacketListener packetListener;
	Activity activity = MainActivity.this;
	Chat chat;
	String opt_jid;
	LinkedList<HashMap<String, String>> getallPositionlist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		opt_jid = "35_test" + "@" + XMPP.HOST + "/Smack";

		attemptLogin();

		textView1 = (TextView) findViewById(R.id.textView1);
		btn_ValuesAdd = (Button) findViewById(R.id.btn_ValuesAdd);
		btnprintlist = (Button) findViewById(R.id.btnprintlist);
		btn_ValuesGet = (Button) findViewById(R.id.btn_ValuesGet);
		btn_ValuesGetAll = (Button) findViewById(R.id.btn_ValuesGetAll);
		btn_ValuesUpdate = (Button) findViewById(R.id.btn_ValuesUpdate);
		btn_ValuesDelete = (Button) findViewById(R.id.btn_ValuesDelete);
		button1 = (Button) findViewById(R.id.button1);

		btn_ValuesAdd.setOnClickListener(this);
		btn_ValuesGet.setOnClickListener(this);
		btn_ValuesGetAll.setOnClickListener(this);
		btn_ValuesUpdate.setOnClickListener(this);
		btn_ValuesDelete.setOnClickListener(this);
		btnprintlist.setOnClickListener(this);
		button1.setOnClickListener(this);

	}

	/**
	 * It use for add and remove message listener.
	 *
	 * @param chatCreated
     */
	void onChatCreated(Chat chatCreated) {
		if (chat != null) {
			if (StringUtils.parseBareAddress(chat.getParticipant())
					.equals(StringUtils.parseBareAddress(chatCreated.getParticipant()))) {
				chat.removeMessageListener(messageListener);
				chat = chatCreated;
				chat.addMessageListener(messageListener);
			}
		} else {
			chat = chatCreated;
			chat.addMessageListener(messageListener);
		}
	}

	/**
	 * Send message
	 *
	 * @param message
     */
	void sendMessage(String message) {
		if (chat != null) {
			try {
				chat.sendMessage(message);
				Message msg = new Message();
				msg.setTo(StringUtils.parseBareAddress(opt_jid));
				msg.setFrom(XMPP.getInstance().getConnection(activity).getUser());

			} catch (NotConnectedException e) {
			} catch (Exception e) {

			}
		}
	}

	/**
	 * Register to xmpp server
	 *
	 * @param paramString1
	 * @param paramString2
     * @return
     */

	private boolean register(String paramString1, String paramString2) {
		try {
			XMPP.getInstance().register(paramString1, paramString2);
			return true;
		} catch (XMPPException localXMPPException) {
			localXMPPException.printStackTrace();
		} catch (NoResponseException e) {
			e.printStackTrace();
		} catch (NotConnectedException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Login to xmpp server
	 *
	 * @param user
	 * @param pass
	 * @param username
     * @return
     */

	private boolean login(String user, String pass, String username) {
		try {
			AppSettings.setUser(this, user);
			AppSettings.setPassword(this, pass);
			AppSettings.setUserName(this, username);

			XMPP.getInstance().login(user, pass, username);
			sendBroadcast(new Intent("liveapp.loggedin"));

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				AppSettings.setUser(this, user);
				AppSettings.setPassword(this, pass);
				AppSettings.setUserName(this, username);
				XMPP.getInstance().login(user + "@" + XMPP.HOST, pass, username);
				sendBroadcast(new Intent("liveapp.loggedin"));

				return true;
			} catch (XMPPException e1) {
				e1.printStackTrace();
			} catch (SaslException e1) {
				e1.printStackTrace();
			} catch (SmackException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		return false;
	}

	/**
	 * Async task use to call register the user then login it and
	 * got all messages in messageListener, packetListener
	 *
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

		public UserLoginTask() {
		}

		protected Boolean doInBackground(Void... paramVarArgs) {

			String mEmail = "35" + "_test";
			String mUsername = user_name1;
			String mPassword = "welcome";

			if (register("35" + "_test", "welcome")) {
				try {
					XMPP.getInstance().close();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			return login(mEmail, mPassword, mUsername);
		}

		protected void onCancelled() {
			mAuthTask = null;

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

 		protected void onPostExecute(Boolean success) {
			mAuthTask = null;

			if (success) {

				messageListener = new MessageListener() {

					public void processMessage(Chat chatMes, final Message message) {

					}
				};
				packetListener = new PacketListener() {

					@Override
					public void processPacket(final Packet packet) throws NotConnectedException {

						if (packet instanceof Message) {
							final Message message = (Message) packet;

							if ((message.getBody().toString().equals("CurrentPositions"))) {

								String CurrentString = message.getBody().toString();
								String[] separated = CurrentString.split(":");
								String value = separated[1];
								String[] separated1 = value.split(",");

								if (separated1[0].equals("")) {
									try {
										XMPP.getInstance().getConnection(activity)

												.sendPacket(new IQUpdatePosition(
														XMPP.getInstance().getConnection(activity).getUser(),
														String.valueOf("1"), String.valueOf("2"),
														String.valueOf(new Date().getTime())));

									} catch (SmackException.NotConnectedException e) {
										e.printStackTrace();
									}
								} else {
									try {
										XMPP.getInstance().getConnection(activity)
												.sendPacket(new IQCreatePosition(
														XMPP.getInstance().getConnection(activity).getUser(),
														String.valueOf("1"), String.valueOf("2"),
														String.valueOf(new Date().getTime())));
									} catch (SmackException.NotConnectedException e) {
										e.printStackTrace();
									}

								}

							}

						}

					}
				};
				chatListener = new ChatManagerListener() {

					@Override
					public void chatCreated(Chat chatCreated, boolean local) {
						onChatCreated(chatCreated);
					}
				};

				XMPP.getInstance().getConnection(activity).addPacketListener(packetListener, null);

				ChatManager.getInstanceFor(XMPP.getInstance().getConnection(activity)).addChatListener(chatListener);
				ServiceDiscoveryManager sdm = ServiceDiscoveryManager
						.getInstanceFor(XMPP.getInstance().getConnection(activity));

				sdm.addFeature("http://jabber.org/protocol/disco#info");
				sdm.addFeature("jabber:iq:privacy");

				FileTransferNegotiator.setServiceEnabled(XMPP.getInstance().getConnection(activity), true);

				// ******************************************************************************************//
				try {

					String addr1 = StringUtils.parseBareAddress(XMPP.getInstance().getConnection(activity).getUser());

					String addr2 = StringUtils.parseBareAddress(opt_jid);

					if (addr1.compareTo(addr2) > 0) {
						String addr3 = addr2;
						addr2 = addr1;
						addr1 = addr3;
					}
					chat = ChatManager.getInstanceFor(XMPP.getInstance().getConnection(activity))
							.getThreadChat(addr1 + "-" + addr2);

					if (chat == null) {
						chat = ChatManager.getInstanceFor(XMPP.getInstance().getConnection(activity))
								.createChat(opt_jid, addr1 + "-" + addr2, messageListener);

					} else {
						chat.addMessageListener(messageListener);

					}

					ProviderManager.addExtensionProvider("group", "openfire:iq:getallposition",
							GetGroupCommonExtension.getProvider());

				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {

			}
		}
	}

	/**
	 *  Attempt to login
	 *
	 */
	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}

		boolean cancel = false;
		View focusView = null;

		if (cancel) {
			focusView.requestFocus();
		} else {

			mAuthTask = new UserLoginTask();
			mAuthTask.execute((Void) null);
		}
	}

	/**
	 *
	 * Remove the xmpp connetion and listener.
	 *
	 */

	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
			ChatManager.getInstanceFor(XMPP.getInstance().getConnection(activity)).removeChatListener(chatListener);
			if (chat != null && messageListener != null) {
				XMPP.getInstance().getConnection(activity).removePacketListener(packetListener);
				chat.removeMessageListener(messageListener);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * Clicks over all controls.
	 *
	 * @param v
     */

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.button1:
			sendMessage("abc");
			break;

		case R.id.btnprintlist:
			Log.d("tag", "getallPositionlist :" + getallPositionlist);
			break;

		case R.id.btn_ValuesAdd:

			try {
				XMPP.getInstance().getConnection(activity).sendPacket(new IQCreatePosition(XMPP.getInstance().getConnection(activity).getUser().toString(), "bbbbbb",
						(String) "aaabbb", String.valueOf(new Date().getTime())));
			} catch (NotConnectedException e) {
				e.printStackTrace();
			}

			break;

		case R.id.btn_ValuesGetAll:

			loadCommonPositions("aaaaaaa");

			break;
		case R.id.btn_ValuesGet:

			try {
				XMPP.getInstance().getConnection(activity).sendPacket(new IQGetPositionCommon(
						XMPP.getInstance().getConnection(activity).getUser().toString(), XMPP.getInstance().getConnection(activity).getUser().toString()));
			} catch (NotConnectedException e) {
				e.printStackTrace();
			}

			break;
		case R.id.btn_ValuesUpdate:

			try {
				XMPP.getInstance().getConnection(activity)

						.sendPacket(new IQUpdatePosition("aaaaaaa", "abc2222", "aaabbb",
								String.valueOf(new Date().getTime())));

			} catch (NotConnectedException e) {
				e.printStackTrace();
			}

			break;
		case R.id.btn_ValuesDelete:

			try {
				XMPP.getInstance().getConnection(activity).sendPacket(
						new IQRemovePosition(XMPP.getInstance().getConnection(activity).getUser(), "aaaaaaa"));
			} catch (NotConnectedException e) {
				e.printStackTrace();
			}

			break;

		default:
			break;
		}

	}

	/**
	 * Load common positions from server using openfire custom plugin
	 *
	 * @param Id
     */
	private void loadCommonPositions(String Id) {
		PacketListener packetListener = new PacketListener() {

			public void processPacket(Packet packet) {

				if ((packet instanceof Message)) {
					Collection<PacketExtension> extensions = packet.getExtensions();

					getallPositionlist = new LinkedList<HashMap<String, String>>();

					for (PacketExtension extension : extensions) {
						if ((extension instanceof GetGroupCommonExtension)) {
							PositionItem group = ((GetGroupCommonExtension) extension).group;

							HashMap<String, String> hm = new HashMap<String, String>();
							hm.put("postionID", group.postionID);
							hm.put("Id", group.Id);
							hm.put("Lat", group.Lat);
							hm.put("Lng", group.Lng);
							hm.put("creationDate", group.creationDate);

							getallPositionlist.add(hm);

						}
					}

				}
			}
		};

		PacketExtensionFilter filter = new PacketExtensionFilter(IQGetPositionALL.NAMESPACE);
		XMPP.getInstance().getConnection(activity).addPacketListener(packetListener, filter);
		try {
			XMPP.getInstance().getConnection(activity)
					.sendPacket(new IQGetPositionALL(XMPP.getInstance().getConnection(activity).getUser(), Id));
		} catch (NotConnectedException e) {
			e.printStackTrace();
		}

	}

}
