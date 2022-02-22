package org.elegantsoft.app.service

import org.elegantsoft.app.model.Server

import java.util

trait ServerService {
  def create(server: Server): Server
  def ping(ip: String): Server
  def list(limit: Int): Iterable[Server]
  def get(id: Long): Server
  def update(server: Server): Server
  def delete(id: Long): Boolean
}
