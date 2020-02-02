/*
 * Minecraft Forge, Patchwork Project
 * Copyright (c) 2016-2020, 2019-2020
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 2.1
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package net.minecraftforge.fml.network;

import net.minecraft.entity.Entity;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;

import com.patchworkmc.impl.networking.ListenableChannel;
import com.patchworkmc.impl.networking.MessageFactory;
import com.patchworkmc.impl.networking.PatchworkNetworking;

public class NetworkHooks {
	public static boolean onCustomPayload(final ICustomPacket<?> packet, final ClientConnection connection) {
		ListenableChannel target = NetworkRegistry.findListener(packet.getName());

		if (target == null) {
			return false;
		}

		final NetworkEvent.Context context = new NetworkEvent.Context(connection, packet.getDirection(), packet.getIndex());

		target.onPacket(packet, context);

		return context.getPacketHandled();
	}

	public static Packet<?> getEntitySpawningPacket(Entity entity) {
		MessageFactory factory = PatchworkNetworking.getMessageFactory();

		return factory.getEntitySpawningPacket(entity);
	}
}
